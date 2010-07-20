BEGIN;

INSERT INTO entity (current_revision, locale_id) VALUES (1, (SELECT locale_id FROM locale WHERE title = 'English'));
INSERT INTO currency (currency_id) VALUES (currval('entity_ent_id_seq'));
INSERT INTO entityRevision (ent_id, rev, title) VALUES (currval('entity_ent_id_seq'), 1, '€');

INSERT INTO entity (current_revision, locale_id) VALUES (1, (SELECT locale_id FROM locale WHERE title = 'English'));
INSERT INTO currency (currency_id) VALUES (currval('entity_ent_id_seq'));
INSERT INTO entityRevision (ent_id, rev, title) VALUES (currval('entity_ent_id_seq'), 1, '$');

COMMIT;
