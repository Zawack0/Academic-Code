package main

//
// see instructions in ../README.md
//

import (
	"fmt"
	"lab1/lockservice"
	"os"
	"time"
)

func main() {
	if len(os.Args) == 4 && os.Args[1] == "-p" {
		lockservice.StartServer(os.Args[2], os.Args[3], true)
	} else if len(os.Args) == 4 && os.Args[1] == "-b" {
		lockservice.StartServer(os.Args[2], os.Args[3], false)
	} else {
		fmt.Printf("Usage: lockd -p|-b primaryport backupport\n")
		os.Exit(1)
	}
	for {
		time.Sleep(100 * time.Second)
	}
}
