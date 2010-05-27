CREATE TABLE property (
	prop_id BIGINT NOT NULL,

	name varchar(50) NOT NULL,
	title varchar(50) NOT NULL,
	
	PRIMARY KEY (prop_id),
	FOREIGN KEY (prop_id) REFERENCES entity (ent_id)
);
