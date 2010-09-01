CREATE TABLE receiptEntry (
	rid BIGINT NOT NULL,
	pid BIGINT NOT NULL,
	price BIGINT NOT NULL,

	FOREIGN KEY (rid) REFERENCES entity (ent_id)
);