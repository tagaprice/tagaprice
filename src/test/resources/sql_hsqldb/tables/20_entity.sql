CREATE TABLE entity (
	ent_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	locale_id INTEGER, --todo fix this... NOT NULL,

	created_at TIMESTAMP DEFAULT NOW(), --TODO fix NOT NULL,

	current_revision INTEGER,
	
	PRIMARY KEY (ent_id),
--TODO fix	FOREIGN KEY (locale_id) REFERENCES locale(locale_id),
--	CHECK (current_revision >= 1)
);
