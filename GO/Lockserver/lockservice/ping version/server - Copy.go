package lockservice

import (
	"fmt"
	"io"
	"log"
	"net"
	"net/rpc"
	"os"
	"sync"
	"time"
)

type LockServer struct {
	l         net.Listener // you will not need to use or change this
	dead      bool         // for test_test.go
	dying     bool         // for test_test.go
	isPrimary bool         // am I the primary?
	backup    string       // backup's port

	locks map[string]bool // for each lock name, is it locked?
	mu    sync.Mutex      // you may find this useful

	processed map[int64]bool
}

func MakeLockServer(backup string, isPrimary bool) *LockServer {
	ls := new(LockServer)
	ls.backup = backup
	ls.isPrimary = isPrimary
	ls.locks = map[string]bool{}
	ls.processed = map[int64]bool{}
	return ls
}

// server Lock RPC handler.
//
// you will have to modify this function
func (ls *LockServer) Lock(args *LockArgs, reply *LockReply) error {
	ls.mu.Lock()
	defer ls.mu.Unlock()

	locked := ls.locks[args.Lockname]
	processed := ls.processed[args.LockID]

	if processed {
		reply.dup = true
		reply.OK = false
		return nil
	}
	if locked {
		reply.OK = false
	} else {
		reply.OK = true
		ls.locks[args.Lockname] = true
	}

	ls.done(args.LockID)

	if ls.isPrimary {
		ok := call(ls.backup, "LockServer.Ping", args, &reply)
		if ok {
			args.FromClient = false
			call(ls.backup, "LockServer.Lock", args, &reply)
		}
	}
	return nil

}

// server Unlock RPC handler
func (ls *LockServer) Unlock(args *UnlockArgs, reply *UnlockReply) error {

	ls.mu.Lock()
	defer ls.mu.Unlock()

	locked := ls.locks[args.Lockname]
	processed := ls.processed[args.LockID] // keep track of reply here, not completion

	if processed {
		reply.OK = false
		reply.dup = true
		return nil
	}

	if !locked {
		reply.OK = false

	} else {
		reply.OK = true
		ls.locks[args.Lockname] = false
	}
	ls.done(args.LockID)

	if ls.isPrimary {
		ok := call(ls.backup, "LockServer.Ping", args, &reply)
		if ok {
			args.FromClient = false
			call(ls.backup, "LockServer.Unlock", args, &reply)
		}
	}
	return nil

}

func (ls *LockServer) done(id int64) error {
	ls.processed[id] = true
	return nil
}

func (ls *LockServer) ping(args *UnlockArgs, reply *UnlockReply) error {
	return nil
}

// *********************************************************************

// tell the server to shut itself down.
// for testing.
// please don't change this.
func (ls *LockServer) kill() {
	ls.dead = true
	ls.l.Close()
}

// hack to allow test_test.go to have primary process
// an RPC but not send a reply. can't use the shutdown()
// trick b/c that causes client to immediately get an
// error and send to backup before primary does.
// please don't change anything to do with DeafConn.
type DeafConn struct {
	c io.ReadWriteCloser
}

func (dc DeafConn) Write(p []byte) (n int, err error) {
	return len(p), nil
}
func (dc DeafConn) Close() error {
	return dc.c.Close()
}
func (dc DeafConn) Read(p []byte) (n int, err error) {
	return dc.c.Read(p)
}

//
// creates a lockserver and registers the server with net/rpc
// starts a thread to listen for RPC connections from clients
// please do not change or subvert this function
//

func StartServer(primary string, backup string, isPrimary bool) *LockServer {
	ls := MakeLockServer(backup, isPrimary)

	me := ""
	if isPrimary {
		me = primary
	} else {
		me = backup
	}

	// tell net/rpc about our RPC server and handlers.
	rpcs := rpc.NewServer()
	rpcs.Register(ls)

	// prepare to receive connections from clients.
	// change "unix" to "tcp" to use over a network.
	os.Remove(me) // only needed for "unix"
	l, e := net.Listen("unix", me)
	if e != nil {
		log.Fatal("listen error: ", e)
	}
	ls.l = l

	// please don't change any of the following code,
	// or do anything to subvert it.

	// create a thread to accept RPC connections from clients.
	go func() {
		for !ls.dead {
			conn, err := ls.l.Accept()
			if err == nil && !ls.dead {
				if ls.dying {
					// process the request but force discard of reply.

					// without this the connection is never closed,
					// b/c ServeConn() is waiting for more requests.
					// test_test.go depends on this two seconds.
					go func() {
						time.Sleep(2 * time.Second)
						conn.Close()
					}()
					ls.l.Close()

					// this object has the type ServeConn expects,
					// but discards writes (i.e. discards the RPC reply).
					deaf_conn := DeafConn{c: conn}

					rpcs.ServeConn(deaf_conn)

					ls.dead = true
				} else {
					go rpcs.ServeConn(conn)
				}
			} else if err == nil {
				conn.Close()
			}
			if err != nil && !ls.dead {
				fmt.Printf("LockServer(%v) accept: %v\n", me, err.Error())
				ls.kill()
			}
		}
	}()

	return ls
}
