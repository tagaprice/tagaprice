CREATE TABLE account (
	uid BIGINT NOT NULL,
	mail VARCHAR(200),
	last_login TIMESTAMP,
	locked boolean DEFAULT true NOT NULL,
	
	PRIMARY KEY (uid),
--	FOREIGN KEY (uid) REFERENCES entity (ent_id),
	UNIQUE(mail)
);

ALTER TABLE entity ADD creator BIGINT;
ALTER TABLE entityRevision ADD creator BIGINT;


