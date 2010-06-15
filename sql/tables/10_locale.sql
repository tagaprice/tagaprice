CREATE TABLE locale (
	locale_id SERIAL NOT NULL,
	fallback_id INTEGER,
	title varchar(50) NOT NULL,
	localTitle varchar(50) NOT NULL,

	created_at TIMESTAMP NOT NULL DEFAULT now(),

	PRIMARY KEY (locale_id),
	FOREIGN KEY (fallback_id) REFERENCES locale (locale_id)
);
