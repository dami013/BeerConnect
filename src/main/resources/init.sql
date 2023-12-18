-- Inizializzazione della tabella Pub
INSERT INTO pub (id_pub, name_pub, country, year_of_foundation)
VALUES
    (1, 'Brewery1', 'Italy', 1990),
    (2, 'CraftBrewery2', 'USA', 2005),
    (3, 'TraditionalBrewery3', 'Germany', 1980),
    (4, 'ArtisanalBrewery4', 'Belgium', 1995),
    (5, 'LocalBrewery5', 'UK', 2010);


-- Inizializzazione della tabella Beer
INSERT INTO beer (id_beer, name_beer, type, aroma, alcohol, color, country, ingredients, price, quantity_in_stock, id_pub, beer_type)
VALUES
    (1,'IPA', 'India Pale Ale', 'Citrusy', 6.5, 'Amber', 'USA', 'Water, Malt, Hops, Yeast', 5.99, 100, 2, 'beer'),
    (2,'Stout', 'Stout', 'Roasty', 7.0, 'Dark Brown', 'Ireland', 'Water, Barley, Hops, Yeast', 7.99, 75, 3, 'beer'),
    (3,'BelgianTripel', 'Tripel', 'Fruity', 8.0, 'Golden', 'Belgium', 'Water, Malt, Sugar, Hops, Yeast', 9.99, 80, 4, 'beer'),
    (4,'Porter', 'Porter', 'Chocolatey', 6.8, 'Black', 'UK', 'Water, Malt, Hops, Yeast', 8.49, 60, 5, 'beer'),
    (5,'Weizenbock', 'Weizenbock', 'Banana, Clove', 7.5, 'Dark Amber', 'Germany', 'Water, Wheat, Munich Malt, Hops, Yeast', 10.99, 50, 3, 'beer');

-- Inizializzazione della tabella LimitedEdition (sottoclasse di Beer)
INSERT INTO beer (name_beer, type, aroma, alcohol, color, country, ingredients, price, quantity_in_stock, beer_type, le_beer, production_year)
VALUES
    ('LimitedEdition1', 'Limited Edition', 'Special Aroma', 8.5, 'Gold', 'Belgium', 'Special Ingredients', 12.99, 50, 'limited', 'LimitedEdition1', 2023),
    ('LimitedEdition2', 'Limited Edition', 'Unique Blend', 9.0, 'Copper', 'USA', 'Rare Ingredients', 14.99, 40, 'limited', 'LimitedEdition2', 2022),
    ('LimitedEdition3', 'Limited Edition', 'Exotic Notes', 8.8, 'Ruby Red', 'Germany', 'Rare Ingredients', 13.49, 45, 'limited', 'LimitedEdition3', 2024),
    ('LimitedEdition4', 'Limited Edition', 'Smokey Finish', 9.2, 'Deep Brown', 'UK', 'Special Ingredients', 15.99, 30, 'limited', 'LimitedEdition4', 2021),
    ('LimitedEdition5', 'Limited Edition', 'Elegant Hops', 8.7, 'Amber', 'Belgium', 'Rare Ingredients', 14.49, 35, 'limited', 'LimitedEdition5', 2025);

-- Inizializzazione della tabella Client
INSERT INTO client (id_client,name_client, email, date_birth, address, preferences)
VALUES
    (1,'John Doe', 'john.doe@example.com', 1985, '123 Main St', 'IPAs, Stouts'),
    (2,'Jane Smith', 'jane.smith@example.com', 1990, '456 Oak St', 'Lagers, Porters'),
    (3,'Bob Johnson', 'bob.johnson@example.com', 1982, '789 Pine St', 'Belgians, Pale Ales'),
    (4,'Alice Brown', 'alice.brown@example.com', 1988, '101 Maple St', 'Wheat Beers, Sours'),
    (5,'Charlie Wilson', 'charlie.wilson@example.com', 1995, '202 Elm St', 'Stouts, Limited Editions');

-- Associazione tra Client e Beer (Many-to-Many)
INSERT INTO client_review (id_client, id_beer)
VALUES (1, 1), (1, 2), (2, 3), (3, 4), (4, 5);

-- Aggiornamento della quantità in stock delle birre
UPDATE beer SET quantity_in_stock = quantity_in_stock - 1 WHERE id_beer IN (1, 2, 3, 4, 5);
