CREATE TABLE localAccount (
  uid BIGINT NOT NULL,
  password varchar(32) NOT NULL,
  salt char(10) NOT NULL,

  PRIMARY KEY (uid),
  FOREIGN KEY (uid) REFERENCES account(uid)
);
