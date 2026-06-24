#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username postgres --dbname postgres <<-EOSQL
	CREATE DATABASE gtfse;
  GRANT ALL PRIVILEGES ON DATABASE gtfse TO postgres;
EOSQL

psql -v ON_ERROR_STOP=1 --username postgres --dbname "gtfse" <<-EOSQL
  CREATE EXTENSION IF NOT EXISTS postgis;
EOSQL