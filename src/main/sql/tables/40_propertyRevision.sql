create table propertyRevision (
	prop_id BIGINT NOT NULL,
	rev integer NOT NULL,

        name varchar(50) NOT NULL,
        minValue integer,
        maxValue integer,
        unit_id bigint, -- reference unit
        uniq boolean not null default true,

        PRIMARY KEY (prop_id, rev),
        FOREIGN KEY (prop_id) REFERENCES entity (ent_id),
	FOREIGN KEY (prop_id, rev) REFERENCES entityrevision (ent_id, rev),
        FOREIGN KEY (unit_id) REFERENCES unit (unit_id)
)
