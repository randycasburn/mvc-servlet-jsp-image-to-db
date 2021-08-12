-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER thing_owner WITH PASSWORD 'thing_owner1';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO thing_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO thing_owner;

CREATE USER thing_appuser WITH PASSWORD 'thing_appuser1';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO thing_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO thing_appuser;
