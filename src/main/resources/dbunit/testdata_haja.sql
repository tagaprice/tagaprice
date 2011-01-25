-- Rechnung 1, ein product, billa
-- Product
INSERT INTO entiyRevision(ent_id, rev, title, created_at, creator) VALUES ('1', '1', 'Nöm Vollm. Fl. 1L', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('1', '1', 'l', '1', '1', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('1', '1');
INSERT INTO product(ent_id) VALUES ('1');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('1', '1', '1', '1', '0.99');

-- rechnung 2, hofer
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('2', '1', 'ZZU BIO Weichkäse', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('2', '1', 'g', '250', '1', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('2', '1');
INSERT INTO product(ent_id) VALUES ('2');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '2', '1', '1', '1.58');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('3', '1', 'Karotten-/Kürbiskernbrot', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('3', '1', 'g', '500', '2', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('3', '1');
INSERT INTO product(ent_id) VALUES ('3');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '3', '1', '1', '1.69');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('4', '1', 'Karotten', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('4', '1', 'g', '500', '3', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('4', '1');
INSERT INTO product(ent_id) VALUES ('4');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '4', '1', '1', '0.79');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('5', '1', 'BIO Faschiertes', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('5', '1', 'g', '500', '4', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('5', '1');
INSERT INTO product(ent_id) VALUES ('5');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '5', '1', '1', '3.99');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('6', '1', 'Holländischer Gouda', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('6', '1', 'kg', '1', '1', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('6', '1');
INSERT INTO product(ent_id) VALUES ('6');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '6', '1', '0.47', '3.48'); -- price per unit
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('7', '1', 'Orig. Ital. Spezialitäten', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('7', '1', 'g', '100', '4', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('7', '1');
INSERT INTO product(ent_id) VALUES ('7');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '7', '1', '1', '2.19');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('8', '1', 'BIO-Kiwi', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('8', '1', 'piece', '6', '5', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('8', '1');
INSERT INTO product(ent_id) VALUES ('8');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '8', '1', '2', '1.29');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('9', '1', 'Räucherlachs', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('9', '1', 'g', '200', '6', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('9', '1');
INSERT INTO product(ent_id) VALUES ('9');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '9', '1', '3', '3.49');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('10', '1', 'Jungzwiebel', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('10', '1', 'piece', '1', '3', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('10', '1');
INSERT INTO product(ent_id) VALUES ('10');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '10', '1', '4', '0.99');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('11', '1', 'Zucchini', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('11', '1', 'g', '500', '3', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('11', '1');
INSERT INTO product(ent_id) VALUES ('11');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '11', '1', '1', '0.75');
-- Product
INSERT INTO entityRevision(ent_id, rev, title, created_at, creator) VALUES ('12', '1', 'Baguette sortiert', DEFAULT, '1');
INSERT INTO productRevision(ent_id, rev, unit, amount, category_id, imageUrl) VALUES ('12', '1', 'g', '300', '2', 'someImageUrl');
INSERT INTO entity(ent_id, locale_id) VALUES ('12', '1');
INSERT INTO product(ent_id) VALUES ('12');
-- ReceiptEntry
INSERT INTO receiptEntry(receipt_id, product_id, product_revision, product_count, price) VALUES ('2', '12', '1', '2', '0.79');





-- Receipt
INSERT INTO receipt(receipt_id, shop_id, created_at, creator) VALUES ('1', '1', default, '1');
-- Receipt
INSERT INTO receipt(receipt_id, shop_id, created_at, creator) VALUES ('2', '2', default, '1');
