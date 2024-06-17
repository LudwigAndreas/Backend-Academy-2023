CREATE TABLE item
(
    item_id      BIGINT,
    catalogue_id BIGINT,
    brand_id     BIGINT,
    name         VARCHAR,
    description  VARCHAR,
    itemurl      VARCHAR,
    type         VARCHAR,
    i            VARCHAR,
    brand        VARCHAR,
    catalogue    VARCHAR
);

CREATE TABLE item_sku
(
    item_id BIGINT,
    sku     VARCHAR
);

CREATE TABLE remain
(
    item_id   BIGINT,
    region_id BIGINT,
    price     BIGINT
);

CREATE TABLE catalogue
(
    catalogue_id BIGINT,
    realcatname  VARCHAR,
    image        varchar,
    parent_id    BIGINT,
    name         VARCHAR
);

-- Inserting data into the 'catalogue' table
INSERT INTO catalogue (catalogue_id, realcatname, image, parent_id, name)
VALUES
    (1, 'Electronics', 'electronics.jpg', NULL, 'Electronics'),
    (2, 'Clothing', 'clothing.jpg', NULL, 'Clothing'),
    (3, 'Laptops', 'laptops.jpg', 1, 'Laptops'),
    (4, 'Smartphones', 'smartphones.jpg', 1, 'Smartphones'),
    (5, 'T-Shirts', 'tshirts.jpg', 2, 'T-Shirts'),
    (6, 'Dresses', 'dresses.jpg', 2, 'Dresses'),
    (7, 'Accessories', 'accessories.jpg', NULL, 'Accessories'),
    (8, 'Hats', 'hats.jpg', 7, 'Hats'),
    (9, 'Bags', 'bags.jpg', 7, 'Bags'),
    (10, 'Caps', 'caps.jpg', 8, 'Caps'),
    (11, 'Backpacks', 'backpacks.jpg', 9, 'Backpacks');

-- Inserting data into the 'item' table
INSERT INTO item (item_id, catalogue_id, brand_id, name, description, itemurl, type, i, brand, catalogue)
VALUES
    (101, 1, 1, 'Laptop Model X', 'High-performance laptop', 'laptopmodelx.com', 'Electronics', 'A123', 'BrandX', 'Laptops'),
    (102, 1, 1, 'Tablet Pro', 'Advanced tablet for professionals', 'tabletpro.com', 'Electronics', 'J123', 'BrandG', 'Laptops'),
    (103, 1, 2, 'Smartwatch Series Z', 'Feature-rich smartwatch', 'smartwatchz.com', 'Electronics', 'K456', 'BrandH', 'Laptops'),
    (104, 1, 2, 'Smartphone Y', 'Top-notch smartphone', 'smartphoney.com', 'Electronics', 'B456', 'BrandY', 'Smartphones'),
    (105, 2, 3, 'Casual T-Shirt', 'Comfortable cotton t-shirt', 'tshirtco.com', 'Clothing', 'C789', 'BrandZ', 'T-Shirts'),
    (106, 2, 3, 'Formal Shirt', 'Elegant formal shirt', 'formalshirt.com', 'Clothing', 'L789', 'BrandI', 'T-Shirts'),
    (107, 2, 4, 'Denim Jeans', 'Classic denim jeans', 'denimjeans.com', 'Clothing', 'M012', 'BrandJ', 'T-Shirts'),
    (108, 2, 4, 'Elegant Dress', 'Stylish evening dress', 'dressworld.com', 'Clothing', 'D012', 'BrandA', 'Dresses'),
    (109, 7, 5, 'Phone Case', 'Protective phone case', 'phonecase.com', 'Accessories', 'E345', 'BrandB', 'Accessories'),
    (110, 7, 5, 'Wireless Earbuds', 'High-quality wireless earbuds', 'wirelessearbuds.com', 'Accessories', 'N345', 'BrandK', 'Accessories'),
    (111, 7, 6, 'Leather Wallet', 'Genuine leather wallet', 'leatherwallet.com', 'Accessories', 'O678', 'BrandL', 'Accessories'),
    (112, 8, 6, 'Summer Hat', 'Cool summer hat', 'summerhat.com', 'Hats', 'F678', 'BrandC', 'Hats'),
    (113, 9, 7, 'Travel Backpack', 'Spacious travel backpack', 'travelbackpack.com', 'Bags', 'G901', 'BrandD', 'Bags'),
    (114, 10, 8, 'Baseball Cap', 'Classic baseball cap', 'baseballcap.com', 'Caps', 'H234', 'BrandE', 'Caps'),
    (115, 11, 9, 'Casual Backpack', 'Everyday use backpack', 'casualbackpack.com', 'Backpacks', 'I567', 'BrandF', 'Backpacks'),
    (116, 11, 9, 'Рюкзак-сумка', 'Многофункцилнальный рюкзак', 'backpack-bag.com', 'Backpacks', 'X123', 'BrandF', 'Backpacks'),
    (117, 11, 9, 'Рюкзак кролик', 'Рюкзак в виде кролика женский', 'backpack-rabbit.com', 'Backpacks', 'Z456', 'BrandF', 'Backpacks');

-- Inserting data into the 'item_sku' table
INSERT INTO item_sku (item_id, sku)
VALUES
    (101, '100001'),
    (102, '100002'),
    (103, '100003'),
    (104, '100004'),
    (105, '100005'),
    (106, 'SKU006'),
    (107, 'SKU007'),
    (108, 'SKU008'),
    (109, 'SKU009'),
    (110, 'SKU010'),
    (111, 'SKU011'),
    (112, 'SKU012'),
    (113, 'SKU013'),
    (114, 'SKU014'),
    (115, 'SKU015'),
    (116, 'SKU016'),
    (117, 'SKU017');

-- Inserting data into the 'remain' table
INSERT INTO remain (item_id, region_id, price)
VALUES
    (101, 1, 1200),
    (102, 2, 900),
    (103, 1, 20),
    (104, 2, 150),
    (105, 3, 15),
    (106, 4, 30),
    (107, 3, 80),
    (108, 4, 10),
    (109, 5, 45),
    (110, 1, 750),
    (111, 2, 300),
    (112, 1, 45),
    (113, 2, 60),
    (114, 3, 100),
    (115, 4, 50),
    (116, 1, 80),
    (117, 2, 15);