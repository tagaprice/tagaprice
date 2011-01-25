CREATE TABLE entity (
	ent_id BIGINT,
	locale_id INTEGER, --todo fix this... NOT NULL,

	PRIMARY KEY (ent_id),
--TODO fix	FOREIGN KEY (locale_id) REFERENCES locale(locale_id),
--	CHECK (current_revision >= 1)
);
