CREATE TABLE localAccount (
	uid bigint NOT NULL,
	login varchar(40) NOT NULL,
	password varchar(32) NOT NULL,
	mail varchar(255) NOT NULL,

	PRIMARY KEY (uid),
	FOREIGN KEY (uid) REFERENCES account (uid),
	CHECK (login = lower(login))
);
