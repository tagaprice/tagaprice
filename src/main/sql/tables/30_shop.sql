create table shop (
	shop_id BIGINT NOT NULL,
	type_id BIGINT, -- TODO create a shopType table and reference it from here
	lat FLOAT,
	lng FLOAT,
	street varchar(100),
	city varchar(100),
	country_code char(2),

	PRIMARY KEY (shop_id),
	FOREIGN KEY (shop_id) REFERENCES entity (ent_id),
	FOREIGN KEY (country_code) REFERENCES country(country_code),
	CHECK (lat BETWEEN -90 AND 90),
	CHECK (lng BETWEEN -180 AND 180)
);
