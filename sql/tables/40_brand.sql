CREATE TABLE brand (
	brand_id BIGINT NOT NULL,
	
	PRIMARY KEY (brand_id),
	FOREIGN KEY (brand_id) REFERENCES brand(brand_id)
);
