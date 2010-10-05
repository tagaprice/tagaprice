#!/bin/bash

USERSCRIPT=./.post_compile.sh

mvn -D maven.test.skip=true package

if [ -x "$USERSCRIPT" ]; then
	$USERSCRIPT
else
	cp target/tagaprice-0.0.1-SNAPSHOT.war /var/lib/tomcat6/webapps/tagaprice.war
fi
