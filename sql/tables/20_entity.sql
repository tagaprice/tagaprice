CREATE TABLE entity (
	ent_id BIGSERIAL NOT NULL,
	locale_id INTEGER NOT NULL,

	created_at TIMESTAMP NOT NULL DEFAULT NOW(),

	current_revision INTEGER,
	
	PRIMARY KEY (ent_id),
	FOREIGN KEY (locale_id) REFERENCES locale(locale_id)
);
