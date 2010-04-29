CREATE TABLE property (
	prop_id bigint NOT NULL,
	name varchar(100) NOT NULL,

	PRIMARY KEY (prop_id),
	UNIQUE (name),
	FOREIGN KEY (prop_id) REFERENCES entity(ent_id),
	CHECK (name = lower(name))
);
