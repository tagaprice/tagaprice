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

function inject() {
	dir="$1"
	shift
	getScript "$dir"|psql tagaprice -f - $*
}

inject tables $*

if [ "$TESTDATA" ]; then
	inject testdata $*
fi
