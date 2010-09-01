CREATE TABLE receiptEntry (
	pkey bigserial NOT NULL, 
	rid BIGINT NOT NULL,
	pid BIGINT NOT NULL,
	price BIGINT NOT NULL,

	PRIMARY KEY (pkey),
	FOREIGN KEY (rid) REFERENCES entity (ent_id)
);