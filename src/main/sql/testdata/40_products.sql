BEGIN;
INSERT INTO entity (locale_id, created_at, current_revision, creator) 
VALUES 
(1,'2010-08-18',1,1);

INSERT INTO entityrevision (ent_id, rev, title, created_at, creator) 
VALUES
(currval('entity_ent_id_seq'), 1, 'First Product', '2010-08-18', 1);

INSERT INTO product (prod_id) VALUES (currval('entity_ent_id_seq'));

INSERT INTO productrevision (prod_id, rev, type_id, imageurl)
VALUES
(currval('entity_ent_id_seq'), 1, (SELECT type_id FROM productType LIMIT 1),'nix.png');



/* INSERT TYPE */
INSERT INTO entity (ent_id, locale_id, created_at, current_revision, creator) 
VALUES 
(20,1,'2010-08-18',1,1);

INSERT INTO entityrevision (ent_id, rev, title, created_at, creator) 
VALUES
(20,1, 'eisen', '2010-08-18', 1);
INSERT INTO producttype (type_id) VALUES (20);


/* INSERT PROPERTIES*/
/* ***** Prop 1 ***** */
INSERT INTO entity (locale_id, created_at, current_revision, creator) 
VALUES 
(1,'2010-08-18',1,1);

INSERT INTO entityrevision (ent_id, rev, title, created_at, creator) 
VALUES
(currval('entity_ent_id_seq'), 1, 'energy', '2010-08-18', 1);

INSERT INTO property (prop_id) VALUES (currval('entity_ent_id_seq'));

INSERT INTO entityproperty (prop_id, ent_id, value, min_rev)
VALUES
(currval('entity_ent_id_seq'), (SELECT max(prod_id) FROM product), '20', 1);

INSERT INTO propertyrevision (prop_id, rev, name, minvalue, maxvalue, type, uniq)
VALUES
(currval('entity_ent_id_seq'),1, 'energy',0,10, 'double',true);


/* ***** Prop 2 ***** */
INSERT INTO entity (locale_id, created_at, current_revision, creator) 
VALUES 
(1,'2010-08-18',1,1);

INSERT INTO entityrevision (ent_id, rev, title, created_at, creator) 
VALUES
(currval('entity_ent_id_seq'),1, 'kph', '2010-08-18', 1);

INSERT INTO property (prop_id) VALUES (currval('entity_ent_id_seq'));

INSERT INTO entityproperty (prop_id, ent_id, value, min_rev)
VALUES
(currval('entity_ent_id_seq'), (SELECT max(prod_id) FROM product), '180', 1);

INSERT INTO propertyrevision (prop_id, rev, name, minvalue, maxvalue, type, uniq)
VALUES
(currval('entity_ent_id_seq'),1, 'kph',0,10, 'int', true);

/* ************** */
COMMIT;
