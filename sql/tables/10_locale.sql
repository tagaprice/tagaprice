CREATE TABLE locale (
	locale_id SERIAL NOT NULL,
	fallback_id INTEGER NOT NULL,
	name varchar(50) NOT NULL,
	localname varchar(50) NOT NULL,

	created_at TIMESTAMP NOT NULL DEFAULT now(),

	PRIMARY KEY (locale_id),
	FOREIGN KEY (fallback_id) REFERENCES locale (locale_id)
);
