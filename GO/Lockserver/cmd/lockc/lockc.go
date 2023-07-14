package main

//
// see instructions in ../README.md
//

import (
	"bufio"
	"fmt"
	"lab1/lockservice"
	"os"
	"strings"
)

func usage() {
	fmt.Printf("Usage: lockc primaryport backupport\n")
	os.Exit(1)
}

func getInput(scanner *bufio.Scanner) []string {
	fmt.Print("Enter command: ")
	scanner.Scan()
	text := scanner.Text()
	text = strings.TrimSuffix(text, "\n")
	userInput := strings.Split(text, " ")
	return userInput
}

func main() {
	if len(os.Args) == 3 {
		scanner := bufio.NewScanner(os.Stdin)
		ck := lockservice.MakeClerk(os.Args[1], os.Args[2])
		var ok bool
		fmt.Println("Command format: -l|-u lockname\nwhere -l is lock and -u is unlock")
		for {
			userInput := getInput(scanner)
			if len(userInput) != 2 {
				fmt.Println("Invalid input.\nCommand format: -l|-u lockname")
				continue
			}
			if userInput[0] == "-l" {
				ok = ck.Lock(userInput[1])
			} else if userInput[0] == "-u" {
				ok = ck.Unlock(userInput[1])
			} else {
				fmt.Println("Invalid input.\nCommand format: -l|-u lockname")
				continue
			}
			fmt.Printf("reply: %v\n", ok)
		}
	} else {
		usage()
	}
}
