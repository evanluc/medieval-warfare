CREATE TABLE users
(
	id bigint NOT NULL,
	username character varying(32),
	password character varying(32),
	CONSTRAINT id_pkey PRIMARY KEY (id),
	CONSTRAINT username_unique UNIQUE (username)
)
WITH (
	OIDS=FALSE
);
ALTER TABLE users
	OWNER TO postgres;