#!/bin/bash

USERSCRIPT=./.post_compile.sh

mvn -D maven.test.skip=true clean resources:resources package
rc="$?"
if [ "$rc" -ne 0 ]; then
	echo "Build failed :(" >&2
	exit "$rc"
fi

if [ -x "$USERSCRIPT" ]; then
	$USERSCRIPT
else
	webappDir=`ls -1d /var/lib/tomcat?/webapps|sort -gr|head -n1`
	cp -v target/tagaprice-0.0.1-SNAPSHOT.war $webappDir/tagaprice.war
fi
