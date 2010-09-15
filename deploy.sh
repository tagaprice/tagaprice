#!/bin/bash
mvn -D maven.test.skip=true package
cp target/tagaprice-0.0.1-SNAPSHOT.war /var/lib/tomcat6/webapps/tagaprice.war
