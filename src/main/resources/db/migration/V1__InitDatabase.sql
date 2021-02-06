create table if not exists "user"
(
  user_id       varchar(36)  not null,
  active        bit          not null,
  first_name    varchar(255) not null,
  last_name     varchar(255) not null,
  password_hash varchar(100) not null,
  username      varchar(255) not null,
  primary key (user_id)
);

create table if not exists user_role
(
  user_id varchar(36) not null,
  role    varchar(36) not null,
  primary key (user_id),
  foreign key (user_id) references "user" (user_id)
);

create table if not exists token
(
  token_id varchar(36) not null,
  value    varchar(36) not null,
  user_id  varchar(36) not null,
  primary key (token_id),
  foreign key (user_id) references "user" (user_id)
);