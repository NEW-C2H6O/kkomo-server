create table member (
    member_id bigint not null auto_increment,
    name varchar(255) not null,
    tag int not null,
    profile_image varchar(255) not null,
    provider varchar(255) not null,
    access_token varchar(255) not null,
    refresh_token varchar(255) not null,
    created_at datetime not null,
    updated_at datetime not null,
    primary key (member_id)
);

create table ott_reservation (
    reservation_id bigint not null auto_increment,
    start_time datetime(6) not null,
    end_time datetime(6) not null,
    member_id bigint not null,
    ott_id bigint not null,
    profile_id bigint not null,
    created_at datetime not null,
    updated_at datetime not null,
    primary key (reservation_id)
);

create table ott (
    ott_id bigint not null auto_increment,
    name varchar(255) not null,
    primary key (ott_id)
);

create table ott_profile (
    profile_id bigint not null auto_increment,
    ott_id bigint not null,
    name varchar(255) not null,
    primary key (profile_id)
);
