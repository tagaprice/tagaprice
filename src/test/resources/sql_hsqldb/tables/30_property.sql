CREATE TABLE property (
	prop_id BIGINT NOT NULL,

	PRIMARY KEY (prop_id),
	FOREIGN KEY (prop_id) REFERENCES entity (ent_id)
);
