CREATE TABLE confirmaccount
(
 uid bigint NOT NULL,
 confirm character varying(200),
 confirm_date timestamp without time zone NOT NULL DEFAULT now(),
 CONSTRAINT confirmaccount_pkey PRIMARY KEY (uid),
 CONSTRAINT confirmaccount_uid_fkey FOREIGN KEY (uid)
      REFERENCES account (uid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION


)