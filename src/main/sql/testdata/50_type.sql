BEGIN;

-- root

INSERT INTO entity 
("locale_id", "current_revision", "creator") 
VALUES (1,1,1);

INSERT INTO entityrevision 
("ent_id","rev","title","creator") 
VALUES (currval('entity_ent_id_seq'),1,'root',1);

INSERT INTO producttype ("type_id") VALUES (currval('entity_ent_id_seq'));

INSERT INTO typerevision 
("type_id", "rev", "parent_id") 
VALUES (currval('entity_ent_id_seq'), 1, null);


-- red

INSERT INTO entity 
("locale_id", "current_revision", "creator") 
VALUES (1,1,1);

INSERT INTO entityrevision 
("ent_id","rev","title","creator") 
VALUES (currval('entity_ent_id_seq'),1,'red',1);

INSERT INTO producttype ("type_id") VALUES (currval('entity_ent_id_seq'));

INSERT INTO typerevision 
("type_id", "rev", "parent_id") 
VALUES (currval('entity_ent_id_seq'), 1, (SELECT ent_id FROM entityrevision WHERE title = 'root'));



/*
-- blue

INSERT INTO entity 
("locale_id", "current_revision", "creator") 
VALUES (1,1,1);

INSERT INTO entityrevision 
("ent_id","rev","title","creator") 
VALUES (currval('entity_ent_id_seq'),1,'blue',1);

INSERT INTO producttype ("type_id") VALUES (currval('entity_ent_id_seq'));


INSERT INTO typerevision 
("type_id", "rev", "parent_id") 
VALUES (currval('entity_ent_id_seq'), 1, (SELECT ent_id FROM entityrevision WHERE title = 'root'));
*/

-- green
/*
INSERT INTO entity 
("locale_id", "current_revision", "creator") 
VALUES (1,1,1);

INSERT INTO entityrevision 
("ent_id","rev","title","creator") 
VALUES (currval('entity_ent_id_seq'),1,'green',1);

INSERT INTO producttype ("type_id") VALUES (currval('entity_ent_id_seq'));

INSERT INTO typerevision 
("type_id", "rev", "parent_id") 
VALUES (currval('entity_ent_id_seq'), 1, (SELECT ent_id FROM entityrevision WHERE title = 'red'));
*/

COMMIT;