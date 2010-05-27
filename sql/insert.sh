#!/bin/bash

IFS='
'

function getScript() {
	dir=$1
	echo 'BEGIN;'
	for file in "$dir/"*.sql; do
		echo '\i' $file
	done
	echo 'COMMIT;'
}



getScript tables| psql tagaprice -f - $*
