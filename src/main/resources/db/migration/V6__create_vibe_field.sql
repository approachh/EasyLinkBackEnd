CREATE TABLE vibe_field (
  id UUID PRIMARY KEY,
  label VARCHAR(255),
  type VARCHAR(255),
  value TEXT,
  vibe_id UUID REFERENCES vibe(id),
  account_vibe_id UUID,
  field_type VARCHAR(255)
  -- Добавь любые другие нужные тебе поля!
);
