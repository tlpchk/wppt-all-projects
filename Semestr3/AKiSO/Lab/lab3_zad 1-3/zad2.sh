#!/bin/sh

for proces in /proc/[0-9]* ;
do	
	cat $proces/status
	echo Licba otwartych plikow:  `sudo ls $proces/fd | wc -l`
done 
