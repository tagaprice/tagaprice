-- Rechnung 1, ein product, billa
-- Product
INSERT INTO entiyRevision(ent_id, rev, title, created_at, creator) VALUES ('1', '1', 'Nöm Vollm. Fl. 1L', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('1', '1', 'l', '1', '1', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('1', '1');
INSERT INTO product(ent_id) VALUES ('1');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('1', '1', '1', '1', '99');

-- rechnung 2, hofer
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('2', '1', 'ZZU BIO Weichkäse', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('2', '1', 'g', '250', '1', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('2', '1');
INSERT INTO product(ent_id) VALUES ('2');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '2', '1', '1', '158');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('3', '1', 'Karotten-/Kürbiskernbrot', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('3', '1', 'g', '500', '2', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('3', '1');
INSERT INTO product(ent_id) VALUES ('3');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '3', '1', '1', '169');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('4', '1', 'Karotten', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('4', '1', 'g', '500', '3', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('4', '1');
INSERT INTO product(ent_id) VALUES ('4');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '4', '1', '1', '79');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('5', '1', 'BIO Faschiertes', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('5', '1', 'g', '500', '4', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('5', '1');
INSERT INTO product(ent_id) VALUES ('5');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '5', '1', '1', '399');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('6', '1', 'Holländischer Gouda', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('6', '1', 'kg', '1', '1', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('6', '1');
INSERT INTO product(ent_id) VALUES ('6');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '6', '1', '0.47', '348'); -- price per unit
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('7', '1', 'Orig. Ital. Spezialitäten', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('7', '1', 'g', '100', '4', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('7', '1');
INSERT INTO product(ent_id) VALUES ('7');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '7', '1', '1', '219');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('8', '1', 'BIO-Kiwi', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('8', '1', 'piece', '6', '5', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('8', '1');
INSERT INTO product(ent_id) VALUES ('8');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '8', '1', '2', '129');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('9', '1', 'Räucherlachs', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('9', '1', 'g', '200', '6', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('9', '1');
INSERT INTO product(ent_id) VALUES ('9');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '9', '1', '3', '349');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('10', '1', 'Jungzwiebel', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('10', '1', 'piece', '1', '3', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('10', '1');
INSERT INTO product(ent_id) VALUES ('10');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '10', '1', '4', '99');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('11', '1', 'Zucchini', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('11', '1', 'g', '500', '3', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('11', '1');
INSERT INTO product(ent_id) VALUES ('11');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '11', '1', '1', '75');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('12', '1', 'Baguette sortiert', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('12', '1', 'g', '300', '2', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('12', '1');
INSERT INTO product(ent_id) VALUES ('12');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '12', '1', '2', '79');

-- Rechnung 3, billa
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('13', '1', 'SanLucar Bananen', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('13', '1', 'kg', '1', '5', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('13', '1');
INSERT INTO product(ent_id) VALUES ('13');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('3', '13', '1', '0.172', '199');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('14', '1', 'Exquisa Kräuter', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('14', '1', 'g', '250', '1', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('14', '1');
INSERT INTO product(ent_id) VALUES ('14');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('3', '14', '1', '1', '125');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('15', '1', 'Clever Jogh 3.6 2', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('15', '1', 'g', '250', '1', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('15', '1');
INSERT INTO product(ent_id) VALUES ('15');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('3', '15', '1', '1', '33');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('16', '1', 'Balist Korn Mix 37', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('16', '1', 'piece', '6', '7', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('16', '1');
INSERT INTO product(ent_id) VALUES ('16');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('3', '16', '1', '1', '69');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('17', '1', 'Grüner Veltliner', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('17', '1', 'l', '1', '8', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('17', '1');
INSERT INTO product(ent_id) VALUES ('17');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('3', '17', '1', '3', '199');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('18', '1', 'Dachsteinbrot', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('18', '1', 'g', '500', '2', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('18', '1');
INSERT INTO product(ent_id) VALUES ('18');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('3', '18', '1', '1', '129');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('19', '1', 'Gösser Märzen Fl.', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('19', '1', 'l', '0.5', '8', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('19', '1');
INSERT INTO product(ent_id) VALUES ('19');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('3', '19', '1', '6', '74');





-- Receipt
INSERT INTO receipt(receipt_id, shop_id, created_at, creator) VALUES ('1', '1', default, '1');
-- Receipt
INSERT INTO receipt(receipt_id, shop_id, created_at, creator) VALUES ('2', '2', default, '1');
-- Receipt
INSERT INTO receipt(receipt_id, shop_id, created_at, creator) VALUES ('3', '1', default, '1');

-- Shop
INSERT INTO shop(shop_id, title, latitude, longitude) VALUES ('1', 'Billa', '48.225353', '16.349485');
-- Shop
INSERT INTO shop(shop_id, title, latitude, longitude) VALUES ('2', 'Hofer', '48.222797', '16.353841');
