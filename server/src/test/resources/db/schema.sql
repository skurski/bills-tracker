DROP TABLE IF EXISTS member;
CREATE TABLE IF NOT EXISTS member (
  id INTEGER IDENTITY,
  name VARCHAR(50),
  lastname VARCHAR(100),
  email VARCHAR(100),
  password VARCHAR(40)
);

DROP TABLE IF EXISTS category;
CREATE TABLE IF NOT EXISTS category (
  id INTEGER IDENTITY,
  member_id INTEGER,
  name VARCHAR(100),
  FOREIGN KEY (member_id) REFERENCES member (id)
);

DROP TABLE IF EXISTS expense;
CREATE TABLE IF NOT EXISTS expense (
  id INTEGER IDENTITY,
  category_id INTEGER,
  title VARCHAR(50),
  amount DECIMAL(6,2),
  shortDate date,
  FOREIGN KEY (category_id) REFERENCES category (id)
);

