#!/bin/bash

USERSCRIPT=./.post_compile.sh

mvn -D maven.test.skip=true package
rc="$?"
if [ "$rc" -ne 0 ]; then
	echo "Build failed :(" >&2
	exit "$rc"
fi

if [ -x "$USERSCRIPT" ]; then
	$USERSCRIPT
else
	cp target/tagaprice-0.0.1-SNAPSHOT.war /var/lib/tomcat6/webapps/tagaprice.war
fi
