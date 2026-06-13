create table user_roles
(
    user_id bigint  not null
        constraint user_roles___fk_user_id
            references users
            on delete cascade,
    role_id integer not null
        constraint user_roles___fk_role_id
            references roles
            on delete restrict
);

create unique index user_roles__index_user_id_role_id
    on user_roles (user_id, role_id);

