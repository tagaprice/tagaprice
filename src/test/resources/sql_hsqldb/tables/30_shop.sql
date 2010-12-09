create table shop (
	shop_id BIGINT NOT NULL,

	PRIMARY KEY (shop_id),
	FOREIGN KEY (shop_id) REFERENCES entity (ent_id)
);
