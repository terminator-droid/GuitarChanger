
INSERT INTO users (first_name, last_name, phone_number, password, address, role)
VALUES ('Matthew', 'McConaughey', '+7 962 742 78 54', 'qwer23234t1', 'Moscow, Nepokorennykh Ave., 1', 'User'),
       ('George', 'McFly', '+7 963 346 34 44', '34gerge323', 'Moscow, Prosveshcheniya ave., 2', 'User'),
       ('Ryan', 'Gosling', '+7 963 732 85 32', 'wef2244t3', 'St. Petersburg, Obydennosti ave., 3', 'User'),
       ('John', 'Snow', '+7 928 252 98 25', 'wert43y34', 'Moscow, Hopelessness str., 4', 'User'),
       ('Harry', 'Potter', '+7 935 662 76 23', 'fqwefgy245', 'St. Petersburg, Oblivion Ave., 5', 'User'),
       ('Morty', 'Sanchez', '+7 925 242 34 53', 'greg45', '45 Dalekikh Ave., Saint Petersburg', 'Admin');

INSERT INTO change_types (description)
VALUES ('Exchange with additional payment to the buyer'),
       ('Exchange with additional payment to the seller'),
       ('Exchange'),
       ('Sale');

INSERT INTO products (timestamp, user_id, price, change_type, change_value, change_wish)
VALUES (now(), '67805d38-d903-44c7-89e4-fd67047b8e33', 50000, 3,null, 'fender telecaster'),
(now() , '5e87f5e7-d18c-4f07-9bb4-1141adc51e49', 30000, 4, null, null),
(now(), '42457a09-e3b3-43cf-8bba-2dceef404f99', 2000, 1, 15000, 'walrus julia'),
(now(), '42457a09-e3b3-43cf-8bba-2dceef404f99', 70000, 2, null, 'gibson les paul'),
(now(), '258ba40a-ff5e-41ab-b656-44b0ed663f37', 60000, 4, null, null),
(now(), '04eef978-f6d2-465b-8066-886e9328c7dd', 45000, 3, null, 'fender telecaster'),
(now(), 'b32091a1-61d1-4de5-b685-a2b346981d1a', 10000, 1, 10000, 'boss dd20'),
(now(), 'b32091a1-61d1-4de5-b685-a2b346981d1a', 150000, 2, 20000, 'custom fender stratocaster'),
(now(), '67805d38-d903-44c7-89e4-fd67047b8e33', 20000, 3, null, 'reverb');

INSERT INTO guitars (product_id, brand, model, description, year, country, pick_ups, fingerboard_wood, media_name)
VALUES ('f02c594a-6902-4e6d-9899-b57e2b49bb71', 'burny', 'les paul', 'Great Japanese stick', 1990, 'Japan', null, 'Rosewood'),
       ('4d2f3e65-0b99-4e7b-8eef-74a7359dbdbe', 'aria', 'pro 2', 'defects on the body', 2000, 'Korea', 'zebra', 'Rosewood'),
       ('a02d9d96-d257-418f-b166-1f111b55d000', 'fender', 'telecaster', 'rich sound of a neck', 1970, 'Japan', null, 'Maple'),
       ('ec29a24f-d5de-458e-a45d-2e7d1616b26f', 'fender stratocaster', 'ST-650SPL', 'rare limited edition', 1990, 'Japan', null, 'Alder'),
       ('d2207ac8-e96e-47e7-b82a-e73ce52ffa6b', 'Music Man', 'Axis Super Sport HH', 'Original case included', 2001, 'Korea', null, 'Rosewood'),
       ('4be2dc2e-7eb1-4415-a6f7-91ee8ee6d4e7', 'Gibson', 'premium plus', 'rare color', 2004, 'USA', 'bb pro', 'Rosewood');

INSERT INTO pedals (product_id, brand, model, description, media_name)
VALUES ('8ff99154-93be-4ab6-a3ca-a443ef67c640', 'boss', 'hyper-metal', 'sounds great'),
       ('ae7faf36-5561-4314-b083-e2e5f73368e6', 'T-rex', 'reptile', 'sounds great'),
       ('72b6234c-3aa4-4f6d-8df4-7a51f9956fac', 'Atomic', 'Amplifier', 'good');
