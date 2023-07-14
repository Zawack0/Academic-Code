package viewservice

import (
	"fmt"
)

// the viewservice Clerk lives in the client
// and maintains a little state.
type Clerk struct {
	me     string // client's name (host:port)
	server string // viewservice's host:port
}

func MakeClerk(me string, server string) *Clerk {
	ck := new(Clerk)
	ck.me = me
	ck.server = server
	return ck
}

func (ck *Clerk) Ping(viewnum uint) (View, error) {
	// prepare the arguments.
	args := &PingArgs{}
	args.Me = ck.me
	args.Viewnum = viewnum
	var reply PingReply

	// send an RPC request, wait for the reply.
	ok := call(ck.server, "ViewServer.Ping", args, &reply)
	if !ok {
		return View{}, fmt.Errorf("Ping(%v) failed", viewnum)
	}

	return reply.View, nil
}

func (ck *Clerk) Get() (View, bool) {
	args := &GetArgs{}
	var reply GetReply
	ok := call(ck.server, "ViewServer.Get", args, &reply)
	if !ok {
		return View{}, false
	}
	return reply.View, true
}

func (ck *Clerk) Primary() string {
	v, ok := ck.Get()
	if ok {
		return v.Primary
	}
	return "aaa"
}
