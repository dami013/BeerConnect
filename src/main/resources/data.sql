-- Inserisci alcuni dati per la tabella Pub
INSERT INTO pub (id_pub, name_pub, country, year_of_foundation) VALUES
    (1,'Settimo', 'Italy', 2000),
    (2,'Löwengrube', 'Germany', 1995),
    (3, 'The Green Dragon', 'United Kingdom', 1990),
    (4, 'Bavarian BrewHouse', 'Germany', 2002),
    (5, 'The Rusty Mug', 'United States', 1998),
    (6, 'La Brasserie Royale', 'France', 2003),
    (7, 'The Shamrock Inn', 'Ireland', 1985),
    (8, 'El Tapas Bar', 'Spain', 2006),
    (9, 'Sakura Sake House', 'Japan', 1992);

-- Inserisci alcuni dati per la tabella Beer (normal)
INSERT INTO beer (id_beer,name_beer, type, aroma, alcohol, color, country, ingredients, price, quantity_in_stock, original_beer,beer_type,production_year, id_pub) VALUES
    (1,'Slalom', 'Weiss', 'Smoky', 9.0, 'Yellow', 'Germany', 'Hops, Smoked Malt', 5.5, 100,null,'normal',1990, 1),
    (2, 'Roasty Espresso Stout', 'Stout', 'Roasty', 7.0, 'Dark Brown', 'Ireland', 'Barley, Hops, Coffee Beans', 6.5, 75, null, 'normal', null, 2),
    (3, 'Hoppy Harvest', 'IPA', 'Citrusy', 7.5, 'Amber', 'Italy', 'Barley, Hops, Grapefruit Peel', 5.5, 120, null, 'normal', 2021, 3),
    (4, 'Wheaty Wonderland', 'Wheat Beer', 'Spicy', 5.2, 'Cloudy', 'Belgium', 'Wheat, Coriander, Orange Peel', 4.8, 90, null, 'normal', 2020, 4),
    (5, 'Smoked Porter Perfection', 'Porter', 'Smoky', 6.8, 'Dark Brown', 'USA', 'Barley, Hops, Smoked Malt', 6.0, 80, null, 'normal', 2019, 5),
    (6, 'Golden Haze Hefeweizen', 'Hefeweizen', 'Fruity', 5.4, 'Golden', 'Germany', 'Wheat, Hops, Banana Extract', 4.2, 110, null, 'normal', 2022, 6),
    (7, 'Crisp Pilsner Pleasure', 'Pilsner', 'Herbal', 4.8, 'Light Gold', 'Czech Republic', 'Barley, Saaz Hops', 3.9, 130, null, 'normal', null, 7),
    (8, 'Cherry Blossom Saison', 'Saison', 'Floral', 6.0, 'Pink', 'Japan', 'Barley, Hops, Cherry Blossom Petals', 5.0, 100, null, 'normal', 2023, 8),
    (9, 'Scotch Ale Symphony', 'Scotch Ale', 'Rich', 8.2, 'Deep Amber', 'Scotland', 'Barley, Hops, Peated Malt', 7.5, 70, null, 'normal', 2018, 9),
    (10, 'Mango Tango Pale Ale', 'Pale Ale', 'Tropical', 5.6, 'Pale Amber', 'Australia', 'Barley, Hops, Mango Puree', 6.2, 85, null, 'normal', 2020, 1);

-- Inserisci alcuni dati per la tabella LimitedEdition
INSERT INTO beer (id_beer,name_beer, type, aroma, alcohol, color, country, ingredients, price, quantity_in_stock, original_beer,beer_type, production_year, id_pub) VALUES
    (11,'Xmas Slalom', 'Weiss', 'Smoky', 7.0, 'Yellow', 'Germany', 'Hops, Smoked Malt, Cinnamon', 6.0, 50, 'Slalom','limited', 2022, 1),
    (12,'Limited Hoppy', 'IPA', 'Citrusy', 7.5, 'Amber', 'Italy', 'Barley, Hops, Grapefruit Peel', 6.0, 75, 'Hoppy Harvest','limited', 2021, 3),
    (13, 'Rare Amber Reserve', 'Amber Ale', 'Caramel', 7.8, 'Deep Amber', 'Belgium', 'Barley, Hops, Caramel Syrup', 8.5, 30, 'Wheaty Wonderland', 'limited', 2023, 4),
    (14, 'Midnight Velvet Stout', 'Stout', 'Velvety', 9.2, 'Jet Black', 'USA', 'Barley, Hops, Cocoa Nibs', 10.0, 40, 'Smoked Porter Perfection', 'limited', 2022, 5),
    (15, 'Enchanting Elixir IPA', 'IPA', 'Magical Citrus', 8.5, 'Enchanting Gold', 'Ireland', 'Barley, Hops, Orange Zest', 9.8, 25, 'Slalom', 'limited', 2023, 5);

-- Inserisci alcuni dati per la tabella Client
INSERT INTO client (id_client, name_client, email, date_birth, address, preferences) VALUES
    (1,'Damian Ficher', 'damian.ficher@example.com', 2000, '222 Station Road', 'IPA'),
    (2,'Claude Roche', 'claude.roche@example.com', 1940, '1 Oak Avenue', 'Pale Ale'),
    (3, 'John Doe', 'john.doe@example.com', 1982, '123 Main Street', 'IPA'),
    (4, 'Jane Smith', 'jane.smith@example.com', 1995, '456 Oak Avenue', 'Stout'),
    (5, 'Alice Johnson', 'alice.johnson@example.com', 1988, '789 Pine Lane', 'Porter'),
    (6, 'Bob Miller', 'bob.miller@example.com', 1976, '101 Cedar Road', 'Wheat Beer'),
    (7, 'Emily Brown', 'emily.brown@example.com', 1991, '202 Elm Street', 'Pale Ale'),
    (8, 'David Taylor', 'david.taylor@example.com', 1980, '303 Birch Boulevard', 'Porter'),
    (9, 'Sophia Anderson', 'sophia.anderson@example.com', 1993, '404 Maple Court', 'Amber Ale'),
    (10, 'Michael Wilson', 'michael.wilson@example.com', 1979, '505 Willow Drive', 'Amber Ale'),
    (11, 'Olivia White', 'olivia.white@example.com', 1986, '606 Cherry Lane', 'Saison'),
    (12, 'Daniel Hall', 'daniel.hall@example.com', 1998, '707 Pineapple Avenue', 'Pilsner');

INSERT INTO client_review (id_review, id_beer, id_client, review, rating) VALUES
    (1, 2, 2, 'Very good taste', 4),
    (2, 2, 3, 'Excellent beer with a coffee twist! Loved it!', 5),
    (3, 2, 4, 'This stout is rich and velvety. A must-try!', 4),
    (4, 5, 3, 'The Smoked Porter Perfection has a refreshing citrus burst. Great choice!', 4.5),
    (5, 6, 3, 'This is golden perfection. Amazing!', 5),
    (6, 8, 5, 'Fantastic Saison with a delightful herbal touch.', 4.5),
    (7, 8, 8, 'Orrible!!', 2),
    (8, 8, 9, 'This Japanese Beer has a unique floral aroma. Enjoyed it!', 4.5),
    (9, 10, 5, 'Mango Tango Pale Ale is a tropical paradise in a glass.', 5),
    (10, 13, 12, 'Rare Amber Reserve is a beer lovers dream. Outstanding!', 4),
    (11, 12, 12, 'The citrus is crisp and refreshing. Perfect for any occasion.', 4);

INSERT INTO client_to_client(client_id, id_client_followed) VALUES
    (2, 1),
    (3, 2),
    (4, 3),
    (5, 4),
    (6, 5),
    (7, 6),
    (8, 7),
    (9, 8),
    (10, 9),
    (11, 10),
    (1, 11),
    (2, 3),
    (4, 6),
    (8, 10),
    (3, 5),
    (7, 9),
    (1, 4),
    (2, 6),
    (5, 8),
    (10, 12);