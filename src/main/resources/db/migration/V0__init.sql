CREATE TABLE accounts
(
    id             INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    account_number numeric                                  NOT NULL,
    type           VARCHAR(20)                              NOT NULL,
    balance        numeric(10, 2) DEFAULT 0                 NOT NULL,
    owner_id       INTEGER                                  NOT NULL,
    CONSTRAINT accounts_pkey PRIMARY KEY (id)
);

CREATE TABLE transactions
(
    id                  INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    amount              numeric(10, 2)                           NOT NULL,
    description         VARCHAR(40),
    type                VARCHAR(20)                              NOT NULL,
    to_account_number   numeric,
    from_account_number numeric,
    transaction_date    TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT transactions_pkey PRIMARY KEY (id)
);

CREATE TABLE users
(
    id                INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    username          VARCHAR(255)                             NOT NULL,
    password          VARCHAR(255)                             NOT NULL,
    first_name        VARCHAR(255)                             NOT NULL,
    last_name         VARCHAR(255)                             NOT NULL,
    phone_number      VARCHAR(20)                              NOT NULL,
    email             VARCHAR(255)                             NOT NULL,
    security_question VARCHAR(255),
    security_answer   VARCHAR(255),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

ALTER TABLE accounts
    ADD CONSTRAINT accounts_account_number_key UNIQUE (account_number);

ALTER TABLE users
    ADD CONSTRAINT users_email_key UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT users_phone_number_key UNIQUE (phone_number);

ALTER TABLE users
    ADD CONSTRAINT users_username_key UNIQUE (username);

ALTER TABLE accounts
    ADD CONSTRAINT accounts_user_id_fkey FOREIGN KEY (owner_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;