CREATE EXTENSION IF NOT EXISTS "pgcrypto";
INSERT INTO question_template (id, text, predefined, created_at) VALUES
  (gen_random_uuid(), 'What is your favorite book?', true, now()),
  (gen_random_uuid(), 'What is your favorite movie?', true, now()),
  (gen_random_uuid(), 'What smell reminds you of home?', true, now()),
  (gen_random_uuid(), 'What do you eat when you want to feel safe?', true, now()),
  (gen_random_uuid(), 'What color brings you peace?', true, now()),
  (gen_random_uuid(), 'What sound reminds you of childhood?', true, now()),
  (gen_random_uuid(), 'What moment would you live again?', true, now());
