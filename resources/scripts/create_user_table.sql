CREATE TABLE users
(
	id SERIAL PRIMARY KEY,
	username character varying(32),
	password character varying(32),
	CONSTRAINT username_unique UNIQUE (username)
)
WITH (
	OIDS=FALSE
);
ALTER TABLE users
	OWNER TO postgres;