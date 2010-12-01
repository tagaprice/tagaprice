CREATE TABLE entity (
	ent_id BIGINT NOT NULL,
	locale_id INTEGER NOT NULL,

	created_at TIMESTAMP DEFAULT NOW() NOT NULL,

	current_revision INTEGER,
	
	PRIMARY KEY (ent_id),
--	FOREIGN KEY (locale_id) REFERENCES locale(locale_id),
--	CHECK (current_revision >= 1) 
);
