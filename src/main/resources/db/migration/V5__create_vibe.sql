CREATE TABLE vibe (
  id UUID PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  type VARCHAR(255),
  vibe_account_id UUID REFERENCES vibe_account(id)
);
