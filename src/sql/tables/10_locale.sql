CREATE TABLE locale (
	locale_id SERIAL NOT NULL,
	locale_code varchar(5) NOT NULL,
	fallback_id integer,

	PRIMARY KEY (locale_id),
	FOREIGN KEY (fallback_id) REFERENCES locale (locale_id)
);
