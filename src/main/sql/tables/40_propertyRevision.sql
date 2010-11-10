create type propertyType AS ENUM (
	'string',
	'double',
	'int'
);


create table propertyRevision (
	prop_id BIGINT NOT NULL,
	rev integer NOT NULL,

	name varchar(50) NOT NULL,
	minValue integer,
	maxValue integer,
	type propertyType NOT NULL,
	uniq boolean not null default true, 
	unit_id BIGINT, -- TODO: foreign key

	PRIMARY KEY (prop_id, rev),
	FOREIGN KEY (prop_id) REFERENCES property (prop_id), 
	FOREIGN KEY (unit_id) REFERENCES unit (unit_id),
	FOREIGN KEY (prop_id, rev) REFERENCES entityrevision (ent_id, rev),
	CHECK (LENGTH(name) > 0 AND substr(name,0,1) = LOWER(substr(name,0,1)))
);
