INSERT INTO shop (shop_id, title, latitude, longitude) VALUES ('3','billa','48.175912','16.32995');
INSERT INTO receipt (receipt_id, shop_id, created_at, creator) VALUES ('4','3',default,'2');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('21','1','Carnaroli',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('21','1','g','200','1','none');
INSERT INTO entity(ent_id, locale_id) VALUES ('21','1');
INSERT INTO product(ent_id) VALUES ('21');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('4','21','1','2','2.99');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('22','1','Gouda',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('22','1','kg','1','1','none');
INSERT INTO entity(ent_id, locale_id) VALUES ('22','1');
INSERT INTO product(ent_id) VALUES ('22');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('4','22','1','2','1.19');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('23','1','Gurken',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('23','1','l','0.5','3','www.gemüse.at');
INSERT INTO entity(ent_id, locale_id) VALUES ('23','1');
INSERT INTO product(ent_id) VALUES ('23');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('4','23','1','2','0.79');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('24','1','Zitronen',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('24','1','kg','2','3','none');
INSERT INTO entity(ent_id, locale_id) VALUES ('24','1');
INSERT INTO product(ent_id) VALUES ('24');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('4','24','1','1','1.79');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('25','1','Schinken',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('25','1','g','200','4','www.fleisch.at');
INSERT INTO entity(ent_id, locale_id) VALUES ('25','1');
INSERT INTO product(ent_id) VALUES ('25');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('4','25','1','1','2.09');


INSERT INTO receipt (receipt_id, shop_id, created_at, creator) VALUES ('5','3',default,'2');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('26','1','Debreziner',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('26','1','piece','4','4','ww.debreziner.com');
INSERT INTO entity(ent_id, locale_id) VALUES ('26','1');
INSERT INTO product(ent_id) VALUES ('26');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('5','26','1','1','1.99');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('27','1','Erdaepfel',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('27','1','kg','2','3','none');
INSERT INTO entity(ent_id, locale_id) VALUES ('27','1');
INSERT INTO product(ent_id) VALUES ('27');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('5','27','1','1','1.59');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('28','1','erdaepfel',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('28','1','kg','1','3','none');
INSERT INTO entity(ent_id, locale_id) VALUES ('28','1');
INSERT INTO product(ent_id) VALUES ('28');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('5','28','1','1','0.80');


INSERT INTO receipt (receipt_id, shop_id, created_at, creator) VALUES ('6','3',default,'2');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('29','1','Mehrkorn 6er',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('29','1','piece','6','3','none');
INSERT INTO entity(ent_id, locale_id) VALUES ('29','1');
INSERT INTO product(ent_id) VALUES ('29');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('6','29','1','1','0.79');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('30','1','Joghurt',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('30','1','ml','500','1','none');
INSERT INTO entity(ent_id, locale_id) VALUES ('30','1');
INSERT INTO product(ent_id) VALUES ('30');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('6','30','1','1','0.99');


INSERT INTO receipt (receipt_id, shop_id, created_at, creator) VALUES ('7','3',default,'2');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('31','1','Fruchjoghurt Birne',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('31','1','ml','500','1','none');
INSERT INTO entity(ent_id, locale_id) VALUES ('31','1');
INSERT INTO product(ent_id) VALUES ('31');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('7','31','1','1','0.65');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('32','1','Kochmagazin',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('32','1','piece','1','9','www.kochen-leicht-gemacht.de');
INSERT INTO entity(ent_id, locale_id) VALUES ('32','1');
INSERT INTO product(ent_id) VALUES ('32');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('7','32','1','1','1.00');


INSERT INTO receipt (receipt_id, shop_id, created_at, creator) VALUES ('8','3',default,'2');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('33','1','Coca Cola',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('33','1','ml','1000','10','www.coca-cola.com');
INSERT INTO entity(ent_id, locale_id) VALUES ('33','1');
INSERT INTO product(ent_id) VALUES ('33');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('8','33','1','2','0.95');

INSERT INTO receipt (receipt_id, shop_id, created_at, creator) VALUES ('9','3',default,'2');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('34','1','Orangen',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('34','1','kg','0.5','5','www.orange.com');
INSERT INTO entity(ent_id, locale_id) VALUES ('34','1');
INSERT INTO product(ent_id) VALUES ('34');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('9','34','1','1','1.93');


INSERT INTO shop (shop_id, title, latitude, longitude) VALUES ('4','Merkur','48.171172','16.340311');

INSERT INTO receipt (receipt_id, shop_id, created_at, creator) VALUES ('10','4',default,'2');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('10','27','1','2','1.39');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('10','33','1','2','1.10');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('10','26','1','4','2.29');

INSERT INTO receipt (receipt_id, shop_id, created_at, creator) VALUES ('11','4',default,'2');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('35','1','Tomaten',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('35','1','kg','1','3','none');
INSERT INTO entity(ent_id, locale_id) VALUES ('35','1');
INSERT INTO product(ent_id) VALUES ('35');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('11','35','1','1','2.99');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('36','1','Blaetterteig',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('36','1','kg','0.5','11','none');
INSERT INTO entity(ent_id, locale_id) VALUES ('36','1');
INSERT INTO product(ent_id) VALUES ('36');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('11','36','1','1','2.00');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('11','23','1','3','0.99');
INSERT INTO shop (shop_id, title, latitude, longitude) VALUES ('5','Spar','48.171172','16.340311');


INSERT INTO receipt (receipt_id, shop_id, created_at, creator) VALUES ('12','4',default,'2');
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('37','1','Mozzarella',default,'2');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('37','1','g','250','1','none');
INSERT INTO entity(ent_id, locale_id) VALUES ('37','1');
INSERT INTO product(ent_id) VALUES ('37');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('12','37','1','1','2.49');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('12','27','1','1','1.59');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('12','33','1','2','1.09');
INSERT INTO receipt (receipt_id, shop_id, created_at, creator) VALUES ('13','4',default,'2');
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('13','36','1','3','0.90');
