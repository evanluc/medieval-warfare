-- Table: users

-- DROP TABLE users;

CREATE TABLE users
(
  username character varying(32), -- This is the username for a user account.
  password character varying(32),
  id bigint NOT NULL,
  CONSTRAINT id_pkey PRIMARY KEY (id),
  CONSTRAINT username_unique UNIQUE (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO postgres;
COMMENT ON COLUMN users.username IS 'This is the username for a user account. ';

