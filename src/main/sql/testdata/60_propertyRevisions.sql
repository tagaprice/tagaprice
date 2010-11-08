BEGIN;

INSERT INTO entity 
(locale_id,current_revision,creator)
VALUES 
(1,1,1);

INSERT INTO entityrevision 
("ent_id","rev","title","creator") 
VALUES 
(currval('entity_ent_id_seq'),1,'proteins',1);

INSERT INTO property 
("prop_id") VALUES 
(currval('entity_ent_id_seq'));

INSERT INTO propertyrevision 
("prop_id","rev","name","minvalue","maxvalue","type","uniq") 
VALUES 
(currval('entity_ent_id_seq'),1,'Proteins',0,1000,'double',TRUE);

COMMIT;