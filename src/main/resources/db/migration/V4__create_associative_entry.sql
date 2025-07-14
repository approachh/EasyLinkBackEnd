CREATE TABLE associative_entry (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    answer_hash VARCHAR(255) NOT NULL,
    associative_question VARCHAR(255) NOT NULL,
    real_question UUID,
    vibe_account UUID REFERENCES vibe_account(id)
);