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
)
