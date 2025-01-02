CREATE TABLE active_code(
    active_code_id bigint not null auto_increment,
    value varchar(255) not null,
    created_at datetime not null,
    updated_at datetime not null,
    primary key (active_code_id)
)