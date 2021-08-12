#!/bin/bash
BASEDIR=$(dirname $0)
psql -U postgres -f "$BASEDIR/dropdb.sql" &&
createdb -U postgres thing &&
psql -U postgres -d thing -f "$BASEDIR/schema.sql" &&
psql -U postgres -d thing -f "$BASEDIR/user.sql" &&
psql -U postgres -d thing -f "$BASEDIR/data.sql"
