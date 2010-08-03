BEGIN;

INSERT INTO entity (current_revision, locale_id, creator) VALUES (1,
	(SELECT locale_id FROM locale WHERE title = 'English'),
	(SELECT ent_id FROM entityRevision WHERE title = 'tagaprice system')
);
INSERT INTO entityRevision (ent_id, rev, title, creator) VALUES (
	currval('entity_ent_id_seq'), 1, 'kg',
	(SELECT ent_id FROM entityRevision WHERE title = 'tagaprice system')
);
INSERT INTO unit(unit_id, currentRevision) VALUES (currval('entity_ent_id_seq'), 1);
INSERT INTO unitRevision(unit_id, rev) VALUES (currval('entity_ent_id_seq'), 1);

INSERT INTO entity (current_revision, locale_id, creator) VALUES (1,
	(SELECT locale_id FROM locale WHERE title = 'English'),
	(SELECT ent_id FROM entityRevision WHERE title = 'tagaprice system')
);
INSERT INTO entityRevision (ent_id, rev, title, creator) VALUES (
	currval('entity_ent_id_seq'), 1, 'g',
	(SELECT ent_id FROM entityRevision WHERE title = 'tagaprice system')
);
INSERT INTO unit(unit_id, currentRevision) VALUES (currval('entity_ent_id_seq'), 1);
INSERT INTO unitRevision(unit_id, rev, base_id, factor) VALUES (currval('entity_ent_id_seq'), 1, (SELECT ent_id FROM entityRevision WHERE title = 'kg' LIMIT 1), 0.001);

INSERT INTO entity (current_revision, locale_id, creator) VALUES (1,
	(SELECT locale_id FROM locale WHERE title = 'English'),
	(SELECT ent_id FROM entityRevision WHERE title = 'tagaprice system')
);
INSERT INTO entityRevision (ent_id, rev, title, creator) VALUES (
	currval('entity_ent_id_seq'), 1, 't',
	(SELECT ent_id FROM entityRevision WHERE title = 'tagaprice system')
);
INSERT INTO unit(unit_id, currentRevision) VALUES (currval('entity_ent_id_seq'), 1);
INSERT INTO unitRevision(unit_id, rev, base_id, factor) VALUES (currval('entity_ent_id_seq'), 1, (SELECT ent_id FROM entityRevision WHERE title = 'kg' LIMIT 1), 1000);

COMMIT;
