CREATE TABLE receipt (
	rid BIGINT NOT NULL,
	sid BIGINT NOT NULL,
	draft BOOLEAN NOT NULL DEFAULT true,
	
	PRIMARY KEY (rid),
	FOREIGN KEY (rid) REFERENCES entity (ent_id) 
);