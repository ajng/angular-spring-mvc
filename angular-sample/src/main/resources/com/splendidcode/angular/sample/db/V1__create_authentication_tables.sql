CREATE TABLE auth_info
(
  username VARCHAR(100) PRIMARY KEY NOT NULL,
  password VARCHAR(48) NOT NULL,
  salt VARCHAR(48) NOT NULL,
  iterations INT NOT NULL
);
