BEGIN;

--
-- this script is used for inserting testdata in a "real" db (such as the postgres test db) so that the DatabaseManager 
-- (package org.tagaprice.server.dao.helper) can generate xml-testdata which is currently saved in WEB-INF/testdata.
--

INSERT INTO locale (locale_id, fallback_id, title, localtitle, created_at) VALUES 
('1','1','english','english',NOW());

INSERT INTO entity (ent_id,locale_id,created_at,current_revision,creator) VALUES
('1','1',NOW(),'2','3'),
('2','1',NOW(),'2','3'),
('3','1',NOW(),'1','3');

INSERT INTO entityRevision (ent_id,rev,title,created_at,creator) VALUES
('1','1','product1',NOW(),'3'),
('1','2','product1',NOW(),'3'),
('2','1','product2',NOW(),'3'),
('2','2','product2',NOW(),'3'),
('3','1','account1',NOW(),'3');

INSERT INTO account (uid,mail,last_login,locked) VALUES
('3','user@mail.com',NOW(),'false');

INSERT INTO product (prod_id) VALUES
('1'),
('2');


INSERT INTO productRevision (prod_id,rev,imageurl) VALUES
('1','1','www.urlToImage.com'),
('1','2','www.differentUrlToImage.com'),
('2','1','www.image.com'),
('2','2','www.image.org');

COMMIT;