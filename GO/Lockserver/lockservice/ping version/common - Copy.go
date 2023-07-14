package lockservice

import (
	"crypto/rand"
	"math/big"
	"net/rpc"
)

//
// RPC definitions for a simple lock service.
//
// You will need to modify this file.
//

// Lock(lockname) returns OK=true if the lock is not held.
// If it is held, it returns OK=false immediately.
type LockArgs struct {
	//add id here
	// Go's net/rpc requires that these field
	// names start with upper case letters!
	Lockname   string // lock name
	LockID     int64  // lock id
	FromClient bool   //
}

type LockReply struct {
	OK  bool
	dup bool
}

// Unlock(lockname) returns OK=true if the lock was held.
// It returns OK=false if the lock was not held.
type UnlockArgs struct {
	Lockname   string
	LockID     int64 // lock id
	FromClient bool
}

type UnlockReply struct {
	OK  bool
	dup bool
}

// large random number generator
// you may find this useful
// use this for unique id!
func nrand() int64 {
	max := big.NewInt(int64(1) << 62)
	bigx, _ := rand.Int(rand.Reader, max)
	x := bigx.Int64()
	return x
}

// *********************************************************************

// call() sends an RPC to the rpcname handler on server srv
// with arguments args, waits for the reply, and leaves the
// reply in reply. the reply argument should be the address
// of a reply structure.
//
// call() returns true if the server responded, and false
// if call() was not able to contact the server. in particular,
// reply's contents are valid if and only if call() returned true.
//
// you should assume that call() will time out and return an
// error after a while if it doesn't get a reply from the server.
//
// please use call() to send all RPCs, in client.go and server.go.
// please don't change this function.
func call(srv string, rpcname string, args interface{}, reply interface{}) bool {
	c, errx := rpc.Dial("unix", srv)
	if errx != nil {
		return false
	}
	defer c.Close()

	err := c.Call(rpcname, args, reply)
	return err == nil
}
