BEGIN;

SET CONSTRAINTS fkey_entity_creator DEFERRED;
SET CONSTRAINTS fkey_entityRevision_creator DEFERRED;

INSERT INTO entity (current_revision, locale_id, creator) VALUES (1, (
	SELECT locale_id FROM locale WHERE title = 'English'),
	currval('entity_ent_id_seq')
);
INSERT INTO entityRevision (ent_id, rev, title, creator) VALUES (currval('entity_ent_id_seq'), 1, 'tagaprice system', currval('entity_ent_id_seq'));

INSERT INTO account (uid) VALUES (currval('entity_ent_id_seq'));

COMMIT;
