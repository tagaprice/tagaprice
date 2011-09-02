#!/bin/bash
baseUrl=http://localhost:9200
couchHost=localhost
couchPort=5984
couchDb=tagaprice

riverData=`cat src/main/webapp/WEB-INF/classes/elasticsearch/river.json | sed "s/{COUCH_HOST}/$couchHost/" | sed "s/{COUCH_PORT}/$couchPort/" | sed "s/{COUCH_DB}/$couchDb/"`

echo "Creating index..."
curl -XPUT "$baseUrl/tagaprice"
echo
echo "Setting mapping..."
curl -XPUT "$baseUrl/tagaprice/tagaprice/_mapping" -d@src/main/webapp/WEB-INF/classes/elasticsearch/mapping.json
echo
echo "Creating river..."
curl -XPUT "$baseUrl/_river/tagaprice/_meta" "-d$riverData"
echo
