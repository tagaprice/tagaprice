CREATE TABLE session (
	uid BIGINT NOT NULL,
	sid char(32) NOT NULL,
	last_active TIMESTAMP NOT NULL DEFAULT NOW(),

	PRIMARY KEY (sid),
	FOREIGN KEY (uid) REFERENCES account(uid),
);

CREATE INDEX session_uid_key on session(uid);
