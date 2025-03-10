-- Structure of the scheme
CREATE TABLE interview.phones
(
    id            SERIAL PRIMARY KEY,
    model         VARCHAR(255) UNIQUE NOT NULL, -- Model of the phone
    brand         VARCHAR(255)        NOT NULL,
    description   text                NOT NULL, -- High-level description of the phone
    specification JSONB               NOT NULL, -- Deep-level characteristics of the phone
    is_active     BOOLEAN DEFAULT true
);

CREATE INDEX idx_phones_model
    ON interview.phones (model);

