To test your server and client code, peform the following:
1. Open two terminal windows. The first will handle the servers, the second the client
2. In the first terminal, run `./build.sh` to build the server and client applications
3. In same terminal, run the following commands to start the servers in the background:
    1. `./lockd/lockd -p a b &`   (starts the primary server; note the -p flag)
    2. `./lockd/lockd -b a b &`   (starts the backup server; note the -b flag)
4. You should see output like the following:
```
$ ./lockd/lockd -p a b &
[1] 72798
$ ./lockd/lockd -b a b &
[2] 72828
```

5. And if you run the `jobs` command you should see the following:
```
[1]-  Running                 ./lockd/lockd -p a b &
[2]+  Running                 ./lockd/lockd -b a b &
```
6. In the other terminal window, start the client with the following: `./lockc/lockc a b`
7. Test the server/client functionality with the following:
```
$ ./lockc/lockc a b
Usage: -l|-u lockname
where -l is lock and -u is unlock
Enter command: -l lx
reply: true
Enter command: -l lx
reply: false
Enter command: -u lx
reply: true
Enter command: -u lx
reply: false
Enter command: -l lx
reply: true
Enter command: -u ly
reply: false
Enter command: -l ly
reply: true
```
* This should result in the two locks, lx and ly, being in the locked state.
8. Now go back to the first terminal window. Kill the primary by running `fg 1` and then hitting Ctrl+C:
```
$ fg 1
./lockd/lockd -p a b
^C
$ jobs
[2]+  Running                 ./lockd/lockd -b a b &
```
9. Now return to the client and check to see if the backup tracks the state appropriately
```
Enter command: -l lx
reply: false
Enter command: -l ly
reply: false
Enter command: -u lx
reply: true
Enter command: -u ly
reply: true
```

10. Take `.png` screenshots of the terminal windows and include them in the `img` folder along with your submission.
