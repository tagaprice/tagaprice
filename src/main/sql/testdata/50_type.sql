BEGIN;

-- root

INSERT INTO entity 
("ent_id", "locale_id", "current_revision", "creator") 
VALUES (13,1,1,1);

INSERT INTO entityrevision 
("ent_id","rev","title","creator") 
VALUES (13,1,'root',1);

-- red

INSERT INTO entity 
("ent_id", "locale_id", "current_revision", "creator") 
VALUES (14,1,1,1);

INSERT INTO entityrevision 
("ent_id","rev","title","creator") 
VALUES (14,1,'red',1);

-- green

INSERT INTO entity 
("ent_id", "locale_id", "current_revision", "creator") 
VALUES (15,1,1,1);

INSERT INTO entityrevision 
("ent_id","rev","title","creator") 
VALUES (15,1,'green',1);

-- blue

INSERT INTO entity 
("ent_id", "locale_id", "current_revision", "creator") 
VALUES (16,1,1,1);

INSERT INTO entityrevision 
("ent_id","rev","title","creator") 
VALUES (16,1,'blue',1);


--------------------- add product typs -----------
INSERT INTO producttype ("type_id") VALUES (13);
INSERT INTO producttype ("type_id") VALUES (14);
INSERT INTO producttype ("type_id") VALUES (15);
INSERT INTO producttype ("type_id") VALUES (16);


--------------------- createy dependencies -----------
--- root
INSERT INTO typerevision 
("type_id", "rev", "parent_id") 
VALUES (13, 1, null);

--- red
INSERT INTO typerevision 
("type_id", "rev", "parent_id") 
VALUES (14, 1, 13);

--- green
INSERT INTO typerevision 
("type_id", "rev", "parent_id") 
VALUES (15, 1, 14);

--- blue
INSERT INTO typerevision 
("type_id", "rev", "parent_id") 
VALUES (16, 1, 13);

COMMIT;