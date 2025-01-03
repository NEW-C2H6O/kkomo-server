CREATE TABLE activity_code(
    activity_code_id bigint not null auto_increment,
    value varchar(255) not null,
    created_at datetime not null,
    updated_at datetime not null,
    primary key (activity_code_id)
)