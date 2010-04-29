CREATE TABLE entity (
	ent_id BIGSERIAL NOT NULL,
	ent_ts timestamp NOT NULL DEFAULT now(),
	locale_id integer NOT NULL,
	currentRevision integer,

	PRIMARY KEY (ent_id),
	FOREIGN KEY (locale_id) REFERENCES locale (locale_id)
);
