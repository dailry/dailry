CREATE TABLE member (
    id bigint primary key auto_increment,
    username varchar(30) unique,
    password varchar(255),
    nickname varchar(30) unique,
    email varchar (255) unique,
    social_type enum('KAKAO', 'NAVER', 'GOOGLE', 'NONE'),
    social_id varchar(255),
    role enum('ROLE_MEMBER', 'ROLE_ADMIN'),
    created_time datetime,
    updated_time datetime
);

CREATE TABLE dailry (
    id bigint primary key auto_increment,
    member_id bigint,
    title varchar(80),
    created_time datetime,
    updated_time datetime,
    FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE dailry_page (
    id bigint primary key auto_increment,
    dailry_id bigint,
    page_number int,
    background varchar(20),
    thumbnail varchar(255),
    elements json,
    created_time datetime,
    updated_time datetime,
    FOREIGN KEY (dailry_id) REFERENCES dailry(id)
);

CREATE TABLE post (
    id bigint primary key auto_increment,
    member_id bigint,
    content text,
    page_image varchar(255),
    created_time datetime,
    updated_time datetime,
    FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE post_comment (
    id bigint primary key auto_increment,
    post_id bigint,
    member_id bigint,
    content varchar(255),
    created_time datetime,
    updated_time datetime,
    FOREIGN KEY (post_id) REFERENCES post(id),
    FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE post_like (
    id bigint primary key auto_increment,
    post_id bigint,
    member_id bigint,
    created_time datetime,
    updated_time datetime,
    FOREIGN KEY (post_id) REFERENCES post(id),
    FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE hashtag (
    id bigint primary key auto_increment,
    tag_name varchar(50),
    created_time datetime,
    updated_time datetime
);

CREATE TABLE post_hashtag (
    id bigint primary key auto_increment,
    post_id bigint,
    hashtag_id bigint,
    created_time datetime,
    updated_time datetime,
    FOREIGN KEY (post_id) REFERENCES post(id),
    FOREIGN KEY (hashtag_id) REFERENCES hashtag(id)
)