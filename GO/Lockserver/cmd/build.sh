#!/bin/bash
cd lockc
go build
cd ../lockd
go build
cd ..
#./lockc/lockc a b &