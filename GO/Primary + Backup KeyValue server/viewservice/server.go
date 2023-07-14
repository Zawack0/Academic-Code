package viewservice

import (
	"fmt"
	"log"
	"net"
	"net/rpc"
	"os"
	"sync"
	"time"
)

type ViewServer struct {
	mu   sync.Mutex
	l    net.Listener
	dead bool
	me   string

	// Your declarations here.

	timestamps map[string]time.Time //Keeps track of most recent time viewservice has heard of a ping from server string
	isIdle     map[string]bool      //keeps track of any idle servers (ie those who can have rebooted or are available)
	curview    uint                 //current view number
	seen       bool                 //Has curview been seen by primary?
	curPrim    string
	curBack    string
}

func MakeViewServer(me string) *ViewServer {
	vs := new(ViewServer)
	vs.me = me
	// Your vs.* initializations here.
	vs.curview = 0
	vs.seen = false
	vs.timestamps = map[string]time.Time{}
	vs.isIdle = map[string]bool{}
	vs.curPrim = ""
	vs.curBack = ""
	return vs

}

// server Ping RPC handler.
// ping
func (vs *ViewServer) Ping(args *PingArgs, reply *PingReply) error {
	vs.mu.Lock()
	defer vs.mu.Unlock()
	pinger := args.Me
	pingerview := args.Viewnum

	if pingerview == 0 { // if server crashes, it should send 0 as its view number until promoted
		if vs.curview == 0 {
			vs.isIdle[pinger] = true
			vs.tick()
			// vs.curPrim = pinger
			// vs.curview = 1
			// reply.View.Viewnum = vs.curview
			// reply.View.Primary = vs.curPrim
			// vs.curBack = ""
			// reply.View.Backup = ""
			reply.View.Viewnum = vs.curview
			reply.View.Primary = vs.curPrim
			reply.View.Backup = vs.curBack
			vs.timestamps[pinger] = time.Now()
			return nil
		} else {
			vs.isIdle[pinger] = true
			reply.View.Viewnum = 0
			reply.View.Backup = vs.curBack
			reply.View.Primary = vs.curPrim
			vs.timestamps[pinger] = time.Now()
			return nil
		}
	} else {
		vs.isIdle[pinger] = false
	}

	if pinger == vs.curPrim && pingerview == vs.curview { //need to keep track of whether or not the primary has acknowledged the current view
		vs.seen = true
	}

	reply.View.Viewnum = vs.curview
	reply.View.Primary = vs.curPrim
	reply.View.Backup = vs.curBack
	vs.timestamps[pinger] = time.Now()

	return nil
}

// server Get() RPC handler.
// to be more seriously implemented in part 2
func (vs *ViewServer) Get(args *GetArgs, reply *GetReply) error {

	reply.View.Backup = vs.curBack
	reply.View.Primary = vs.curPrim
	reply.View.Viewnum = vs.curview

	return nil
}

// tick() is called once per PingInterval; it should notice
// if servers have died or recovered, and change the view
// accordingly.
// QUESTION, if we should only increment the view number when the current primary has
// seen current view number, does this method just do NOTHING if current view number hasn't been seen?
// because we shouldn't change any primary/backup assignments if we don't change viewnumber
func (vs *ViewServer) tick() {

	if !vs.seen && vs.curview != 0 {
		return
	}
	nextView := vs.curview + 1
	oldback := vs.curBack
	oldprim := vs.curPrim
	if vs.curview == 0 || vs.curBack == "" || vs.curPrim == "" { //server start, assign backup or primary as needed
		for servers := range vs.isIdle { //iterate through all availalbe servers
			if vs.isIdle[servers] && vs.curPrim == "" && servers != "" {
				vs.curPrim = servers //if no primary and server is idle, make new primary
				vs.isIdle[servers] = false
			} else if vs.isIdle[servers] && vs.curBack == "" && vs.curPrim != servers && oldprim == vs.curPrim {
				vs.curBack = servers //if primary exists, no backup, and server is idle, make new backup
				vs.isIdle[servers] = false
			}
		}
		if vs.curPrim != "" && vs.curview == 0 { //if we successfully made a primary, and server has not started, then "server start"
			vs.curview = 1
			vs.isIdle[vs.curPrim] = false
			return
		}
	}
	//check if either primary or backup is not responding
	if time.Since(vs.timestamps[vs.curPrim]) >= PingInterval*DeadPings { //hold true if current primary has missed a critical number of pings
		vs.curPrim = vs.curBack
		vs.curBack = ""
	}
	if time.Since(vs.timestamps[vs.curBack]) >= PingInterval*DeadPings { //hold true if current backup has missed a critical number of pings
		vs.curBack = ""
	}

	if vs.isIdle[vs.curPrim] && vs.curBack != "" {
		//this should mean that primary crashed and recovered without missing a ping, if this is the case, promote backup, create new backup and update backup info
		vs.curPrim = vs.curBack
		vs.curBack = ""

	}
	if vs.isIdle[vs.curBack] {
		//this should mean that backup crashed and recovered without missing a ping, if this is the case, update backup info
		vs.curBack = "" //makes backup "empty"
	}
	if vs.curPrim != oldprim || vs.curBack != oldback {
		vs.curview = nextView //if the previous view had been seen, move to the next
		vs.seen = false
	}

}

// *********************************************

// tell the server to shut itself down.
// for testing.
// please don't change this function.
func (vs *ViewServer) Kill() {
	vs.dead = true
	vs.l.Close()
}

func StartServer(me string) *ViewServer {
	vs := MakeViewServer(me)

	// tell net/rpc about our RPC server and handlers.
	rpcs := rpc.NewServer()
	rpcs.Register(vs)

	// prepare to receive connections from clients.
	// change "unix" to "tcp" to use over a network.
	os.Remove(vs.me) // only needed for "unix"
	l, e := net.Listen("unix", vs.me)
	if e != nil {
		log.Fatal("listen error: ", e)
	}
	vs.l = l

	// please don't change any of the following code,
	// or do anything to subvert it.

	// create a thread to accept RPC connections from clients.
	go func() {
		for !vs.dead {
			conn, err := vs.l.Accept()
			if err == nil && !vs.dead {
				go rpcs.ServeConn(conn)
			} else if err == nil {
				conn.Close()
			}
			if err != nil && !vs.dead {
				fmt.Printf("ViewServer(%v) accept: %v\n", me, err.Error())
				vs.Kill()
			}
		}
	}()

	// create a thread to call tick() periodically.
	go func() {
		for !vs.dead {
			vs.tick()
			time.Sleep(PingInterval)
		}
	}()

	return vs
}
