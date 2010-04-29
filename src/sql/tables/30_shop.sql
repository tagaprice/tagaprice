CREATE TABLE shop (
	shop_id bigint NOT NULL,
	parent_id bigint,
	lat double precision,
	lng double precision,

	PRIMARY KEY (shop_id),
	FOREIGN KEY (shop_id) REFERENCES entity (ent_id),
	FOREIGN KEY (parent_id) REFERENCES shop(shop_id)
);
