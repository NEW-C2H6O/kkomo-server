ALTER TABLE member
    ADD email VARCHAR(255) NOT NULL;
ALTER TABLE member
    ADD role VARCHAR(255) NOT NULL;

ALTER TABLE member
    MODIFY refresh_token varchar(255) NULL;
ALTER TABLE member
    MODIFY access_token varchar(255) NULL;
