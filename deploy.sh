#!/bin/bash

USERSCRIPT=./.post_compile.sh

mvn -D maven.test.skip=true resources:resources package
rc="$?"
if [ "$rc" -ne 0 ]; then
	echo "Build failed :(" >&2
	exit "$rc"
fi

if [ -x "$USERSCRIPT" ]; then
	$USERSCRIPT
else
	cp target/tagaprice-0.0.1-SNAPSHOT.war /var/lib/tomcat7/webapps/tagaprice.war
fi
