CREATE TABLE account (
	uid BIGINT NOT NULL,
	email VARCHAR(200),
	last_login TIMESTAMP,
	password varchar(32) NOT NULL,
--	locked boolean DEFAULT true NOT NULL,
	
	PRIMARY KEY (uid),
--	FOREIGN KEY (uid) REFERENCES entity (ent_id),
	UNIQUE(email)
);