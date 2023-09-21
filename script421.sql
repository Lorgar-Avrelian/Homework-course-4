ALTER TABLE student
ADD CONSTRAINT age_check CHECK (age >= 16);

ALTER TABLE student
ADD CONSTRAINT name_check UNIQUE (name);

ALTER TABLE faculty
ADD CONSTRAINT color_constraint UNIQUE (name, color);

ALTER TABLE student
ALTER COLUMN age SET DEFAULT 20;