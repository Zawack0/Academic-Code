package lockservice

// the lockservice Clerk lives in the client
// and maintains a little state.
type Clerk struct {
	servers [2]string // primary port, backup port
	luse    int
	uuse    int
}

func MakeClerk(primary string, backup string) *Clerk {
	ck := new(Clerk)
	ck.servers[0] = primary
	ck.servers[1] = backup
	ck.luse = 0
	ck.uuse = 0

	return ck
}

// ask the lock service for a lock.
// returns true if the lock service
// granted the lock, false otherwise.
//
// you will have to modify this function.
func (ck *Clerk) Lock(lockname string) bool {
	// prepare the arguments.
	args := &LockArgs{}
	args.Lockname = lockname
	args.LockID = nrand()
	var reply LockReply
	args.FromClient = true
	// send an RPC request, wait for the reply.
	ok := call(ck.servers[ck.luse], "LockServer.Ping", args, &reply)
	if !ok {
		ck.luse = 1
		//args.FromClient = true
		call(ck.servers[ck.luse], "LockServer.Lock", args, &reply)
	} else {
		ck.luse = 0
		//args.FromClient = true
		call(ck.servers[ck.luse], "LockServer.Lock", args, &reply)
	}
	return reply.OK
}

//
// ask the lock service to unlock a lock.
// returns true if the lock was previously held,
// false otherwise.
//

func (ck *Clerk) Unlock(lockname string) bool {

	args := &UnlockArgs{}
	args.Lockname = lockname
	args.LockID = nrand()
	args.FromClient = true
	var reply UnlockReply

	// send request, wait for reply
	ok := call(ck.servers[ck.uuse], "LockServer.Ping", args, &reply)

	if !ok {
		ck.uuse = 1
		args.FromClient = true
		call(ck.servers[ck.uuse], "LockServer.Unlock", args, &reply)

	} else {
		ck.uuse = 0
		args.FromClient = true
		call(ck.servers[ck.uuse], "LockServer.Unlock", args, &reply)
	}
	return reply.OK

}
