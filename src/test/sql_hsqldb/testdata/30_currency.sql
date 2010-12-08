BEGIN;

INSERT INTO entity (current_revision, locale_id, creator) VALUES (1,
	(SELECT locale_id FROM locale WHERE title = 'English'),
	(SELECT ent_id FROM entityRevision WHERE title = 'tagaprice system')
);
INSERT INTO entityRevision (ent_id, rev, title, creator) VALUES (
	currval('entity_ent_id_seq'), 1, '€',
	(SELECT ent_id FROM entityRevision WHERE title = 'tagaprice system')
);
INSERT INTO currency (currency_id) VALUES (currval('entity_ent_id_seq'));

INSERT INTO entity (current_revision, locale_id, creator) VALUES (1,
	(SELECT locale_id FROM locale WHERE title = 'English'),
	(SELECT ent_id FROM entityRevision WHERE title = 'tagaprice system')
);
INSERT INTO entityRevision (ent_id, rev, title, creator) VALUES (
	currval('entity_ent_id_seq'), 1, '$',
	(SELECT ent_id FROM entityRevision WHERE title = 'tagaprice system')
);
INSERT INTO currency (currency_id) VALUES (currval('entity_ent_id_seq'));

COMMIT;
