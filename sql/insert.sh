#!/bin/bash

IFS='
'

function injectScript() {
	dir=$1
	for file in "$dir/"*.sql; do
		export ON_ERROR_STOP=1
		echo -e "\033[32mInjecting '$file'\033[0m"
		
		if ! psql -v ON_ERROR_STOP=1 tagaprice -f "$file"; then
			echo -e "\033[31mError\033[0m"
			return 1
		fi
	done
}

function inject() {
	dir="$1"
	shift
	#getScript "$dir"|psql tagaprice -f - $*
	injectScript "$dir"
	return $?
}

if inject tables $*; then
	if [ "$TESTDATA" ]; then
		inject testdata $*
	fi
fi
