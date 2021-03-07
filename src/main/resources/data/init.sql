drop table if exists blog_tag;
CREATE TABLE blog_tag (
   id int primary key auto_increment,
   create_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   modify_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   name varchar(50) NOT NULL DEFAULT '' COMMENT '标签名称'
);

CREATE TABLE blog_category (
  id int primary key auto_increment,
  create_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modify_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  name varchar(50) NOT NULL DEFAULT '' COMMENT '分类名称'
);


CREATE TABLE blog_article_tag
(
    id         int primary key auto_increment,
    article_id int not null,
    tag_id     int not null
);

CREATE TABLE blog_blackip (
    id int primary key auto_increment,
    create_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ip varchar(50) NOT NULL DEFAULT '' COMMENT 'ip地址'
);

CREATE TABLE blog_moment (
    id int primary key auto_increment,
    content varchar(2048),
    hits int not null,
    comments int not null,
    create_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_private boolean default false,
    allow_comment boolean default true
);

create table blog_article (
    id int primary key auto_increment,
    title varchar(1024),
    content text,
    summary varchar(2048) comment '摘要',
    alias varchar(64),
    hits int not null,
    comments int not null,
    status tinyint(1) comment '状态',
    feature_image varchar(128) comment '特征图像',
    create_at timestamp not null default current_timestamp,
    modify_at timestamp not null default current_timestamp on update current_timestamp,
    post_at timestamp,
    is_private boolean default false,
    allow_comment boolean default true,
    category_id int not null
);


