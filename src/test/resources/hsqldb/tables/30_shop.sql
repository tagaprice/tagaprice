create table shop (
	shop_id BIGINT NOT NULL,
	title VARCHAR(255) NOT NULL,
	latitude DOUBLE,
	longitude DOUBLE,

	PRIMARY KEY (shop_id),
--FOREIGN KEY (shop_id) REFERENCES entity (ent_id)
);
