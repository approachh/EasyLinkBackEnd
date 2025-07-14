CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE question_template (
    id UUID PRIMARY KEY,
    text VARCHAR(255) NOT NULL UNIQUE,
    predefined BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE vibe_account (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    created TIMESTAMP NOT NULL DEFAULT now(),
    failed_attempts INTEGER NOT NULL DEFAULT 0,
    last_login TIMESTAMP,
    lock_time TIMESTAMP
);

CREATE TABLE early_access_request (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    approved BOOLEAN,
    created_at TIMESTAMP,
    source VARCHAR(255)
);