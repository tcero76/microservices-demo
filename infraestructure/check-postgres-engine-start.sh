#!/bin/bash
# check-postgres-engine-started.sh

apt-get update -y
apt-get install -y postgresql-client curl

pgIsreadyResult=$(pg_isready -h http://postgres)

echo "result status code:" "$pgIsreadyResult"

while [[ ! $pgIsreadyResult == *"accepting connections"* ]]; do
  >&2 echo "Postgres db has not been created yet!"
  sleep 2
  pgIsreadyResult=$(pg_isready -h http://postgres)
done

./cnb/lifecycle/launcher