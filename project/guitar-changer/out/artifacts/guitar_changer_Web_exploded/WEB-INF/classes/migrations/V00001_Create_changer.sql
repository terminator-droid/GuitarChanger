CREATE SCHEMA IF NOT EXISTS project;

CREATE TABLE IF NOT EXISTS users
(
    id           uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    first_name   VARCHAR(32) NOT NULL,
    last_name    VARCHAR(32) NOT NULL,
    phone_number VARCHAR(32) NOT NULL,
    username     VARCHAR(32) NOT NULL,
    password     VARCHAR(16) NOT NULL,
    address      TEXT,
    role         VARCHAR(16) NOT NULL
);


CREATE TABLE IF NOT EXISTS categories
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS brands
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(32),
    category INTEGER REFERENCES categories
);

CREATE TABLE IF NOT EXISTS change_types
(
    change_type SERIAL PRIMARY KEY,
    description text NOT NULL
);

CREATE TABLE IF NOT EXISTS products
(
    id           uuid    DEFAULT gen_random_uuid() PRIMARY KEY,
    timestamp    TIMESTAMP WITHOUT TIME ZONE,
    user_id      uuid REFERENCES users,
    price        DOUBLE PRECISION NOT NULL,
    brand        VARCHAR(32) REFERENCES brands,
    is_closed    BOOLEAN default FALSE,
    change_type  INT              NOT NULL,
    change_value DOUBLE PRECISION,
    change_wish  VARCHAR(32),
    FOREIGN KEY (change_type) REFERENCES change_types (change_type)
);

CREATE TABLE IF NOT EXISTS guitars
(
    id               uuid    DEFAULT gen_random_uuid() PRIMARY KEY,
    timestamp        TIMESTAMP WITHOUT TIME ZONE,
    user_id          uuid REFERENCES users,
    price            DOUBLE PRECISION NOT NULL,
    brand            VARCHAR(32) REFERENCES brands,
    is_closed        BOOLEAN default FALSE,
    change_type      INT              NOT NULL,
    change_value     DOUBLE PRECISION,
    change_wish      VARCHAR(32),
    model            VARCHAR(32)      NOT NULL,
    description      TEXT,
    year             INT,
    country          VARCHAR(32),
    pick_ups         VARCHAR(32),
    fingerboard_wood VARCHAR(32),
    media_name       VARCHAR(32)
);

alter table project.pedals
    add timestamp TIMESTAMP WITHOUT TIME ZONE ;

alter table project.pedals
    add user_id uuid;

alter table project.pedals
    add price double precision ;

alter table project.pedals
    add brand varchar(32);

alter table project.pedals
    add is_closed boolean default false;

alter table project.pedals
    add change_type integer;

alter table project.pedals
    add change_value double precision;

alter table project.pedals
    add change_wish varchar(32);

alter table project.pedals
    add column_name integer;



CREATE TABLE IF NOT EXISTS pedals
(
    id           uuid    DEFAULT gen_random_uuid() PRIMARY KEY,
    timestamp    TIMESTAMP WITHOUT TIME ZONE,
    user_id      uuid REFERENCES users,
    price        DOUBLE PRECISION NOT NULL,
    brand        VARCHAR(32) REFERENCES brands,
    is_closed    BOOLEAN default FALSE,
    change_type  INT              NOT NULL,
    change_value DOUBLE PRECISION,
    change_wish  VARCHAR(32),
    product_id   uuid REFERENCES products on delete cascade,
    model        VARCHAR(32)      NOT NULL,
    description  TEXT,
    media_name   TEXT
);

CREATE TABLE IF NOT EXISTS offers
(
    id           uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    buyer        uuid REFERENCES users,
    interchange  uuid REFERENCES products,
    change_type  INT REFERENCES change_types,
    change_value DOUBLE PRECISION,
    timestamp    TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS offers_products
(
    offer_id   uuid NOT NULL,
    product_id uuid NOT NULL
);

CREATE TABLE IF NOT EXISTS users_liked_products
(
    user_id uuid NOT NULL REFERENCES users,
    product uuid NOT NULL REFERENCES products
);
