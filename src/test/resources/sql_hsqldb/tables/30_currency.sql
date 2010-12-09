create table currency (
	currency_id BIGINT not null,

	PRIMARY KEY (currency_id),
	FOREIGN KEY (currency_id) REFERENCES entity (ent_id)
);
