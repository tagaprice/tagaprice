BEGIN;

CREATE TABLE account (
	uid BIGINT NOT NULL,
	mail VARCHAR(200),
	last_login TIMESTAMP,
	locked boolean NOT NULL DEFAULT true,
	
	PRIMARY KEY (uid),
	FOREIGN KEY (uid) REFERENCES entity (ent_id),
	UNIQUE(mail)
);

ALTER TABLE entity ADD creator BIGINT NOT NULL;
ALTER TABLE entityRevision ADD creator BIGINT NOT NULL;

ALTER TABLE entity ADD CONSTRAINT fkey_entity_creator FOREIGN KEY (creator) REFERENCES account (uid) DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE entityRevision ADD CONSTRAINT fkey_entityRevision_creator FOREIGN KEY (creator) REFERENCES account (uid) DEFERRABLE INITIALLY DEFERRED;

COMMIT;

