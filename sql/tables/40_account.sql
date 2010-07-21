BEGIN;

CREATE TABLE account (
	uid BIGINT NOT NULL,
	
	PRIMARY KEY (uid),
	FOREIGN KEY (uid) REFERENCES entity (ent_id)
);

ALTER TABLE entity ADD creator BIGINT NOT NULL;
ALTER TABLE entityRevision ADD creator BIGINT NOT NULL;

ALTER TABLE entity ADD CONSTRAINT fkey_entity_creator FOREIGN KEY (creator) REFERENCES account (uid) DEFERRABLE;
ALTER TABLE entityRevision ADD CONSTRAINT fkey_entityRevision_creator FOREIGN KEY (creator) REFERENCES account (uid) DEFERRABLE;

COMMIT;

