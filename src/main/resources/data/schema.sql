CREATE TABLE IF NOT EXISTS `blog_article_tag`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `article_id` int(11) NOT NULL,
    `tag_id`     int(11) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `blog_tag`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `tag_name`     varchar(32) NOT NULL,
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `blog_category`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `category_name`     varchar(32) NOT NULL,
    `category_alias`    varchar(32) NULL,
    `default_space` boolean NOT NULL DEFAULT false,
    `private_space` boolean NOT NULL DEFAULT false,
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `blog_article`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `title`     varchar(512) NOT NULL,
    `content`  text NOT NULL,
    `digest`   varchar(1024) NULL,
    `feature_image` varchar(128) NULL,
    `alias` varchar(32) NULL,
    `status` tinyint(1),
    `hits` int(11) NOT NULL DEFAULT 0,
    `comments` int(11) NOT NULL DEFAULT 0,
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    `posted_time` TIMESTAMP NULL,
    `private_article` boolean NOT NULL DEFAULT false,
    `allow_comment` boolean NOT NULL DEFAULT true,
    `category_id` int(11) NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `blog_moment`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `content`  text NOT NULL,
    `status` tinyint(1),
    `hits` int(11) NOT NULL DEFAULT 0,
    `comments` int(11) NOT NULL DEFAULT 0,
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    `posted_time` TIMESTAMP NULL,
    `allow_comment` boolean NOT NULL DEFAULT false,
    `private_moment` boolean NOT NULL DEFAULT false,
    PRIMARY KEY (`id`)
);