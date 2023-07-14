package pbservice

import (
	"net/rpc"
)

const (
	OK             = "OK"
	ErrNoKey       = "ErrNoKey"
	ErrWrongServer = "ErrWrongServer"
	ErrNotBackup   = "NotBackUp"
	ErrQuickCrash  = "QuickCrash"
)

type Err string

type PutArgs struct {
	Key       string
	Value     string
	Forwarded bool
}

type PutReply struct {
	Err Err
}

type GetArgs struct {
	Forwarded bool
	Key       string
}

type GetReply struct {
	Err   Err
	Value string
}

type ForwardArgs struct {
	Putget string
	Key    string
	Value  string
}

type ForwardReply struct {
	Err   Err
	Value string
}

type TransferArgs struct {
	Prim     string
	Viewnumm uint
	Allvals  map[string]string
}

type TransferReply struct {
	Err Err
}

// Your RPC definitions here.

// *************************************************

// call() sends an RPC to the rpcname handler on server srv
// with arguments args, waits for the reply, and leaves the
// reply in reply. the reply argument should be a pointer
// to a reply structure.
//
// the return value is true if the server responded, and false
// if call() was not able to contact the server. in particular,
// the reply's contents are only valid if call() returned true.
//
// you should assume that call() will time out and return an
// error after a while if it doesn't get a reply from the server.
//
// please use call() to send all RPCs, in client.go and server.go.
// please don't change this function.
func call(srv string, rpcname string,
	args interface{}, reply interface{}) bool {
	c, errx := rpc.Dial("unix", srv)
	if errx != nil {
		return false
	}
	defer c.Close()

	err := c.Call(rpcname, args, reply)
	return err == nil
}
