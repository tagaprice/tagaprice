CREATE TABLE localAccount (
  uid BIGINT NOT NULL,
  login varchar(100) NOT NULL,
  pwd varchar(32) NOT NULL,
  salt varchar(10) NOT NULL,
  last_login TIMESTAMP,

  PRIMARY KEY (uid),
  UNIQUE(login),
  FOREIGN KEY (uid) REFERENCES account(uid)
);