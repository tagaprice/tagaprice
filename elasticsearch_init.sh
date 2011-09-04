#!/bin/bash
baseUrl=http://localhost:9200
couchHost=localhost
couchPort=5984
couchDb=tagaprice

if [ -n "$1" ]; then
	baseUrl="$1"
fi

riverData=`cat src/main/webapp/WEB-INF/classes/elasticsearch/river.json | sed "s/{COUCH_HOST}/$couchHost/" | sed "s/{COUCH_PORT}/$couchPort/" | sed "s/{COUCH_DB}/$couchDb/"`

echo "Creating index with mappings..."
curl -XPUT "$baseUrl/tagaprice" -d@src/main/webapp/WEB-INF/classes/elasticsearch/index.json || exit $?
echo
echo "Creating river..."
curl -XPUT "$baseUrl/_river/tagaprice/_meta" "-d$riverData" || exit $?
echo
