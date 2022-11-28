create schema if not exists project;

create table if not exists users
(
    id           uuid primary key,
    full_name    text not null,
    phone_number text not null,
    password     text not null,
    address      text,
    role         text
);

create table if not exists guitars
(
    id               uuid primary key,
    brand            character varying(32) not null,
    model            character varying(32) not null,
    description      text,
    year             integer,
    country          character varying(32),
    pick_ups         character varying(32),
    fingerboard_wood character varying(32),
    sound_board      character varying(32),
    media_name       text
    );

create table if not exists pedals
(
    id          uuid primary key,
    brand       character varying(32) not null,
    model       character varying(32) not null,
    description text,
    media_name  text
    );

create table if not exists change_types
(
    id          uuid primary key,
    description text not null
);

create table if not exists products
(
    id           uuid primary key,
    product_id   uuid,
    price        double precision,
    isChangeable boolean,
    change_type  uuid,
    change_value double precision,
    foreign key (change_type) references change_types,
    foreign key (product_id) references guitars,
    foreign key (product_id) references pedals
    );

create table if not exists users_liked_products
(
    user_id uuid not null references users,
    product uuid not null references products
);

create table if not exists interchanges
(
    id         uuid primary key,
    user_id    uuid references users,
    product_id uuid references products,
    timestamp  timestamp without time zone,
    closed     boolean
);

create table if not exists offers
(
    id           uuid primary key,
    buyer        uuid references users,
    interchange  uuid references interchanges,
    product      uuid array,
    change_type  uuid references change_types,
    change_value double precision
)