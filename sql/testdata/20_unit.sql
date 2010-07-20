BEGIN;

INSERT INTO entity (current_revision, locale_id) VALUES (1, (SELECT locale_id FROM locale WHERE title = 'English'));
INSERT INTO entityRevision (ent_id, rev, title) VALUES (currval('entity_ent_id_seq'), 1, 'kg');
INSERT INTO unit(unit_id) VALUES (currval('entity_ent_id_seq'));

INSERT INTO entity (current_revision, locale_id) VALUES (1, (SELECT locale_id FROM locale WHERE title = 'English'));
INSERT INTO entityRevision (ent_id, rev, title) VALUES (currval('entity_ent_id_seq'), 1, 'g');
INSERT INTO unit(unit_id, fallback_unit, factor) VALUES (currval('entity_ent_id_seq'), (SELECT ent_id FROM entityRevision WHERE title = 'kg' LIMIT 1), 0.001);

INSERT INTO entity (current_revision, locale_id) VALUES (1, (SELECT locale_id FROM locale WHERE title = 'English'));
INSERT INTO entityRevision (ent_id, rev, title) VALUES (currval('entity_ent_id_seq'), 1, 't');
INSERT INTO unit(unit_id, fallback_unit, factor) VALUES (currval('entity_ent_id_seq'), (SELECT ent_id FROM entityRevision WHERE title = 'kg' LIMIT 1), 1000);

COMMIT;
