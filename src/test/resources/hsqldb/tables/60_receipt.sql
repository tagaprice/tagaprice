CREATE TABLE receipt (
	receipt_id BIGINT NOT NULL,
	shop_id BIGINT,
--draft BOOLEAN NOT NULL DEFAULT true,
	created_at TIMESTAMP DEFAULT NOW() NOT NULL,
	creator BIGINT NOT NULL,
	
	PRIMARY KEY (receipt_id),
--  FOREIGN KEY (receipt_id) REFERENCES entity (ent_id) --NOT VERSIONED
	FOREIGN KEY (creator) REFERENCES account (uid)
);
