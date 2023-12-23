-- Inserisci alcuni dati per la tabella Pub
INSERT INTO pub (id_pub, name_pub, country, year_of_foundation) VALUES
                                                            (1,'Pub1', 'Italy', 2000),
                                                            (2,'Pub2', 'Germany', 1995);

-- Inserisci alcuni dati per la tabella Beer (normal)
INSERT INTO beer (id_beer,name_beer, type, aroma, alcohol, color, country, ingredients, price, quantity_in_stock, original_beer,beer_type,production_year, id_pub)
VALUES
    (1,'Beer1', 'Type1', 'Aroma1', 5.0, 'Yellow', 'Italy', 'Ingredients1', 3.5, 100,null,'normal',null, 1),
    (2,'Beer2', 'Type2', 'Aroma2', 6.0, 'Brown', 'Germany', 'Ingredients2', 4.0, 150,null,'normal',null,2);

-- Inserisci alcuni dati per la tabella LimitedEdition
INSERT INTO beer (id_beer,name_beer, type, aroma, alcohol, color, country, ingredients, price, quantity_in_stock, original_beer,beer_type, production_year, id_pub) VALUES
                                                                                                                                                      (3,'LimitedBeer1', 'Type3', 'Aroma3', 7.0, 'Red', 'Belgium', 'Ingredients3', 5.0, 50, 'Beer1','limited', 2022, 1),
                                                                                                                                                      (4,'LimitedBeer2', 'Type4', 'Aroma4', 8.0, 'Black', 'USA', 'Ingredients4', 6.0, 75, 'Beer2','limited', 2021, 2);

-- Inserisci alcuni dati per la tabella Client
INSERT INTO client (id_client, name_client, email, date_birth, address, preferences) VALUES
                                                                              (1,'Client1', 'client1@example.com', 1990, 'Address1', 'Preference1'),
                                                                              (2,'Client2', 'client2@example.com', 1985, 'Address2', 'Preference2');

