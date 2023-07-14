package pbservice

import (
	"lab2/viewservice"
	"time"
)

type Clerk struct {
	vs       *viewservice.Clerk
	hostname string
}

func MakeClerk(vshost string, me string) *Clerk {
	ck := new(Clerk)
	ck.vs = viewservice.MakeClerk(me, vshost)
	ck.hostname = vshost
	ck.vs.Primary()
	return ck
}

// fetch a key's value from the current primary;
// if they key has never been set, return "".
// Get() must keep trying until it either the
// primary replies with the value or the primary
// says the key doesn't exist (has never been Put().
func (ck *Clerk) Get(key string) string {
	for true {
		viewargs := &viewservice.GetArgs{}
		var viewreply viewservice.GetReply
		call(ck.hostname, "ViewServer.Get", viewargs, &viewreply)
		// ^this should give us current view info, including who is primary

		args := &GetArgs{}
		args.Forwarded = false
		args.Key = key
		var reply GetReply
		ok := call(viewreply.View.Primary, "PBServer.Get", args, &reply)
		if !ok { //if primary didn't respond
			time.Sleep(viewservice.PingInterval)
		} else if reply.Err != OK { //if primary responded with an error
			if reply.Err == ErrNoKey {
				return ""
			} else { //if primary responded with any non "no key" error, sleep for a ping interval
				time.Sleep(viewservice.PingInterval)
			}
		} else { //if reply is OK
			return reply.Value
		}
	}
	return "We Goofed"
}

// tell the primary to update key's value.
// must keep trying until it succeeds.
func (ck *Clerk) Put(key string, value string) {
	for true {
		viewargs := &viewservice.GetArgs{}
		var viewreply viewservice.GetReply
		call(ck.hostname, "ViewServer.Get", viewargs, &viewreply)
		// ^this should give us current view info, including who is primary

		args := &PutArgs{}
		args.Forwarded = false
		args.Key = key
		args.Value = value
		var reply PutReply
		ok := call(viewreply.View.Primary, "PBServer.Put", args, &reply)
		if !ok || reply.Err != OK { //if primary didn't respond or responded poorly, sleep
			time.Sleep(viewservice.PingInterval)
		} else {
			return
		}
	}

}
