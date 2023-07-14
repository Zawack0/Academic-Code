package pbservice

import (
	"fmt"
	"lab2/viewservice"
	"log"
	"math/rand"
	"net"
	"net/rpc"
	"os"
	"sync"
	"syscall"
	"time"
)

type PBServer struct {
	mu         sync.Mutex
	l          net.Listener
	dead       bool // for testing
	unreliable bool // for testing
	me         string
	vsname     string
	vs         *viewservice.Clerk
	viewnum    uint
	Primary    string
	Backup     string
	valmap     map[string]string
}

func MakePBServer(me string, vshost string) *PBServer {
	pb := new(PBServer)
	pb.me = me
	pb.vs = viewservice.MakeClerk(me, vshost)
	pb.vsname = vshost
	pb.viewnum = 0
	pb.Primary = ""
	pb.Backup = ""
	pb.valmap = map[string]string{}

	return pb

}

func (pb *PBServer) Get(args *GetArgs, reply *GetReply) error {
	pb.mu.Lock()
	defer pb.mu.Unlock()

	if pb.viewnum == 0 || (pb.me != pb.Backup && pb.me != pb.Primary) { //either we crashed and cameback before a tick, or a request is happening before we ticked the first time
		reply.Err = ErrQuickCrash
		return nil
	}
	fromClient := (!args.Forwarded)
	if pb.me != pb.Primary && fromClient { //if I am backup and this is a client request
		reply.Err = ErrWrongServer
		return nil
	}
	if pb.me != pb.Backup && !fromClient { //if I am primary and this is a forwarded request
		reply.Err = ErrWrongServer
		return nil
	}
	// I have the above as 2 if statements in case I feel the need to differentiate the errors later as part of debugging

	if pb.me == pb.Primary && pb.Backup != "" { // forward request here if i am primary
		Argss := &ForwardArgs{}
		Argss.Putget = "get"
		Argss.Key = args.Key
		var Replyy ForwardReply
		call(pb.Backup, "PBServer.Forward", Argss, &Replyy)
		if Replyy.Err != OK { //if there was a problem forwarding, mission abort
			reply.Err = Replyy.Err
			return nil
		}
	}

	//if we got here, then we should actually process the request
	value, exists := pb.valmap[args.Key]
	if !exists {
		reply.Err = ErrNoKey
		return nil
	} else {
		reply.Value = value
		reply.Err = OK
	}

	return nil
}

func (pb *PBServer) Put(args *PutArgs, reply *PutReply) error {
	pb.mu.Lock()
	defer pb.mu.Unlock()

	if pb.viewnum == 0 || (pb.me != pb.Backup && pb.me != pb.Primary) { //either we crashed and cameback before a tick, or a request is happening before we ticked the first time
		reply.Err = ErrQuickCrash
		return nil
	}
	fromClient := (!args.Forwarded)
	if pb.me != pb.Primary && fromClient { //if i am backup and request is from client
		reply.Err = ErrWrongServer
		return nil
	}
	if pb.me != pb.Backup && !fromClient { //if i am primary and request is forwarded
		reply.Err = ErrWrongServer
		return nil
	}

	if pb.me == pb.Primary && pb.Backup != "" { // forward request here if i am primary
		Argss := &ForwardArgs{}
		Argss.Putget = "put"
		Argss.Key = args.Key
		Argss.Value = args.Value
		var Replyy ForwardReply
		call(pb.Backup, "PBServer.Forward", Argss, &Replyy)
		if Replyy.Err != OK { //if there was a problem forwarding, mission abort
			reply.Err = Replyy.Err
			return nil
		}
	}

	//if we got here, then actually process request here
	pb.valmap[args.Key] = args.Value
	reply.Err = OK
	return nil
}

// Server Forward RPC handler
// forwards request to backup
func (pb *PBServer) Forward(args *ForwardArgs, reply *ForwardReply) error {

	if args.Putget == "put" {
		Argss := &PutArgs{}
		Argss.Forwarded = true
		Argss.Key = args.Key
		Argss.Value = args.Value
		var Replyy GetReply
		call(pb.Backup, "PBServer.Put", Argss, &Replyy) // do i need to handle this?
		if Replyy.Err != OK {
			reply.Err = Replyy.Err
		} else {
			reply.Err = OK
		}
	}
	if args.Putget == "get" {
		Argss := &GetArgs{}
		Argss.Forwarded = true
		Argss.Key = args.Key
		var Replyy GetReply
		call(pb.Backup, "PBServer.Get", Argss, &Replyy) // do i need to handle this?
		if Replyy.Err != OK {
			reply.Err = Replyy.Err
		} else {
			reply.Err = OK
			reply.Value = Replyy.Value
		}
	}
	return nil
}

// Server transfer RPC handler
// responsible for transferring the put/get values stored
func (pb *PBServer) Transfer(args *TransferArgs, reply *TransferReply) error {
	pb.mu.Lock()
	defer pb.mu.Unlock()
	pb.valmap = args.Allvals
	pb.Primary = args.Prim
	pb.Backup = pb.me
	pb.viewnum = args.Viewnumm
	reply.Err = OK
	return nil
}

// ping the viewserver periodically.
// if view changed:
//
//	transition to new view.
//	manage transfer of state from primary to new backup.
func (pb *PBServer) tick() {

	args := &viewservice.PingArgs{}
	args.Me = pb.me
	args.Viewnum = pb.viewnum
	var reply viewservice.PingReply
	call(pb.vsname, "ViewServer.Ping", args, &reply) //ping viewserver, which we assume never crashes
	if pb.viewnum != reply.View.Viewnum {
		pb.viewnum = reply.View.Viewnum
		if pb.Primary != reply.View.Primary {
			pb.Primary = reply.View.Primary
		}
		if pb.Backup != reply.View.Backup && pb.me == pb.Primary {
			if reply.View.Backup == "" {
				pb.Backup = ""
			} else {
				Argss := &TransferArgs{}
				Argss.Allvals = pb.valmap
				Argss.Prim = pb.me
				Argss.Viewnumm = pb.viewnum
				Replyy := &TransferReply{}
				ok := call(reply.View.Backup, "PBServer.Transfer", &Argss, &Replyy)
				if !ok {
					// print("Error report: \n")
					// print("pb.me = " + pb.me + "\n")
					// print("pb.prinary = " + pb.Primary + "\n")
					// print("pb.Backup = " + pb.Backup + "\n")
					// print("view.backup = " + reply.View.Backup + "\n")
					// print("view.primary = " + reply.View.Primary + "\n")
					// print("Failed to transfer to backup")
					for !ok {
						ok = call(reply.View.Backup, "PBServer.Transfer", &Argss, &Replyy)
					}

				}
				if ok && Replyy.Err == OK {
					pb.Backup = reply.View.Backup
				}
			}
		}
	}

}

// *********************************************

// tell the server to shut itself down.
// please do not change this function.
func (pb *PBServer) kill() {
	pb.dead = true
	pb.l.Close()
}

func StartServer(vshost string, me string) *PBServer {
	pb := MakePBServer(me, vshost)
	rpcs := rpc.NewServer()
	rpcs.Register(pb)

	os.Remove(pb.me)
	l, e := net.Listen("unix", pb.me)
	if e != nil {
		log.Fatal("listen error: ", e)
	}
	pb.l = l

	// please do not change any of the following code,
	// or do anything to subvert it.

	go func() {
		for !pb.dead {
			conn, err := pb.l.Accept()
			if err == nil && !pb.dead {
				if pb.unreliable && (rand.Int63()%1000) < 100 {
					// discard the request.
					conn.Close()
				} else if pb.unreliable && (rand.Int63()%1000) < 200 {
					// process the request but force discard of reply.
					c1 := conn.(*net.UnixConn)
					f, _ := c1.File()
					err := syscall.Shutdown(int(f.Fd()), syscall.SHUT_WR)
					if err != nil {
						fmt.Printf("shutdown: %v\n", err)
					}
					go rpcs.ServeConn(conn)
				} else {
					go rpcs.ServeConn(conn)
				}
			} else if err == nil {
				conn.Close()
			}
			if err != nil && !pb.dead {
				fmt.Printf("PBServer(%v) accept: %v\n", me, err.Error())
				pb.kill()
			}
		}
	}()

	go func() {
		for !pb.dead {
			pb.tick()
			time.Sleep(viewservice.PingInterval)
		}
	}()

	return pb
}
