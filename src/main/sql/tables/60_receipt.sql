CREATE TABLE receipt (
	rid BIGINT NOT NULL,
	sid BIGINT,
	draft BOOLEAN NOT NULL DEFAULT true,
	
	PRIMARY KEY (rid),
	FOREIGN KEY (rid) REFERENCES entity (ent_id) 
);