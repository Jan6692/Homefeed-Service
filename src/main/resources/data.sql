-- Sample data initialization
-- Spring Boot picks this file up automatically as long as
-- spring.sql.init.mode=always and spring.jpa.defer-datasource-initialization=true
-- are set (see application.properties).

DELETE FROM greeting;
DELETE FROM sales_banner;
DELETE FROM product;
DELETE FROM app_user;

-- Greeting -------------------------------------------------------------
INSERT INTO greeting (id, language, text) VALUES
                                              (1, 'en', 'Hello!'),
                                              (2, 'de', 'Hallo!'),
                                              (3, 'fr', 'Bonjour!'),
                                              (4, 'es', '¡Hola!'),
                                              (5, 'it', 'Ciao!');

-- App user ---------------------------------------------------------------
INSERT INTO app_user (id, name, last_name, language) VALUES
                                                         (1, 'John', 'Smith', 'en'),
                                                         (2, 'Anna', 'Müller', 'de'),
                                                         (3, 'Marie', 'Dubois', 'fr'),
                                                         (4, 'Carlos', 'García', 'es'),
                                                         (5, 'Giulia', 'Rossi', 'it');

-- Sales banner -----------------------------------------------------------
INSERT INTO sales_banner (id, language, headline, cta_label, picture_url) VALUES
                                                                              (1, 'en', 'Summer Sale - Up to 50% Off',   'Shop Now',     'https://example.com/banners/en-summer.jpg'),
                                                                              (2, 'de', 'Sommerschlussverkauf - Bis zu 50% Rabatt', 'Jetzt einkaufen', 'https://example.com/banners/de-summer.jpg'),
                                                                              (3, 'fr', 'Soldes d''été - Jusqu''à 50% de réduction', 'Acheter maintenant', 'https://example.com/banners/fr-summer.jpg'),
                                                                              (4, 'es', 'Rebajas de verano - Hasta 50% de descuento', 'Comprar ahora', 'https://example.com/banners/es-summer.jpg'),
                                                                              (5, 'it', 'Saldi estivi - Fino al 50% di sconto', 'Acquista ora', 'https://example.com/banners/it-summer.jpg');

-- Product ----------------------------------------------------------------
INSERT INTO product (id, picture_url, price) VALUES
                                                 (1, 'https://example.com/products/sneakers.jpg',    59.99),
                                                 (2, 'https://example.com/products/backpack.jpg',    79.50),
                                                 (3, 'https://example.com/products/jeans.jpg', 129.00),
                                                 (4, 'https://example.com/products/jacket.jpg',       199.90),
                                                 (5, 'https://example.com/products/shirt.jpg',   45.00);
