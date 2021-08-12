-- *************************************************************************************************
-- This script creates all of the database objects (tables, sequences, etc) for the database
-- *************************************************************************************************

BEGIN;

-- CREATE statements go here
DROP TABLE IF EXISTS thing;

create table thing
(
    thing_id serial constraint pk_thing primary key,
    name varchar(40) not null constraint thing_name_key unique,
    avatar bytea
);
COMMIT;
