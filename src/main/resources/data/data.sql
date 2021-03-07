insert into blog_category(name) values ('java');
insert into blog_category(name) values ('life');
insert into blog_category(name) values ('web');
insert into blog_category(name) values ('android');
insert into blog_category(name) values ('kotlin');
insert into blog_category(name) values ('flutter');

/*init blog_tag data*/
insert into blog_tag(name) values ('android');
insert into blog_tag(name) values ('nodejs');
insert into blog_tag(name) values ('netty');
insert into blog_tag(name) values ('ios');
insert into blog_tag(name) values ('ipp');
insert into blog_tag(name) values ('线程池');
insert into blog_tag(name) values ('maven');
insert into blog_tag(name) values ('pom');
insert into blog_tag(name) values ('distribute');
insert into blog_tag(name) values ('repository');
insert into blog_tag(name) values ('thymeleaf');
insert into blog_tag(name) values ('element-ui');
insert into blog_tag(name) values ('iview');
insert into blog_tag(name) values ('json');
insert into blog_tag(name) values ('jackson');
insert into blog_tag(name) values ('fastjson');
insert into blog_tag(name) values ('gson');
insert into blog_tag(name) values ('google');
insert into blog_tag(name) values ('chrome');
insert into blog_tag(name) values ('firefox');
insert into blog_tag(name) values ('ajax');
insert into blog_tag(name) values ('jquery');
insert into blog_tag(name) values ('markdown');
insert into blog_tag(name) values ('csdn');
insert into blog_tag(name) values ('jna');
insert into blog_tag(name) values ('electron');
insert into blog_tag(name) values ('jwt');
insert into blog_tag(name) values ('aes');
insert into blog_tag(name) values ('logback');
insert into blog_tag(name) values ('log4j');
insert into blog_tag(name) values ('image');
insert into blog_tag(name) values ('video');
insert into blog_tag(name) values ('audio');

/*init blog_moment*/
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态2', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动3', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态4', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态5', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态t', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态6', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态7', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态8', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态9', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态20', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态11', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态12', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态13', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态14', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态15', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态16', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态17', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态17', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态120', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态118', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态19', 0, 0, false, true);
insert into blog_moment(content, hits, comments, is_private, allow_comment) values('测试动态112', 0, 0, false, true);


insert into blog_blackip(ip) values('127.0.0.1');
insert into blog_blackip(ip) values('128.0.0.1');
insert into blog_blackip(ip) values('129.0.0.1');
insert into blog_blackip(ip) values('130.0.0.1');
insert into blog_blackip(ip) values('131.0.0.1');
insert into blog_blackip(ip) values('132.0.0.1');
insert into blog_blackip(ip) values('133.0.0.1');
insert into blog_blackip(ip) values('134.0.0.1');
insert into blog_blackip(ip) values('135.0.0.1');
insert into blog_blackip(ip) values('136.0.0.1');
insert into blog_blackip(ip) values('137.0.0.1');
insert into blog_blackip(ip) values('138.0.0.1');
insert into blog_blackip(ip) values('139.0.0.1');
insert into blog_blackip(ip) values('140.0.0.1');
insert into blog_blackip(ip) values('141.0.0.1');
insert into blog_blackip(ip) values('142.0.0.1');
insert into blog_blackip(ip) values('143.0.0.1');
insert into blog_blackip(ip) values('144.0.0.1');
insert into blog_blackip(ip) values('145.0.0.1');
insert into blog_blackip(ip) values('145.0.0.1');
insert into blog_blackip(ip) values('146.0.0.1');
insert into blog_blackip(ip) values('147.0.0.1');
insert into blog_blackip(ip) values('148.0.0.1');
insert into blog_blackip(ip) values('149.0.0.1');
insert into blog_blackip(ip) values('150.0.0.1');
insert into blog_blackip(ip) values('151.0.0.1');


/**init article **/
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 2);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 2);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 3);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 3);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 4);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 4);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 5);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 5);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 6);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 6);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 1);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 1);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 1);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 1);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 1);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 1);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 1);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 1);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 2);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 2);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 2);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 2);
insert into blog_article(title, content, summary, hits, comments,
                         alias, status, create_at, modify_at, post_at, is_private, allow_comment, category_id)
values ('测试文章', '这里是测试文章内容', '', 23, 4, '', 0, now(), now(), now(), false, true, 2);


insert into blog_article_tag(article_id, tag_id) values(2, 12);
insert into blog_article_tag(article_id, tag_id) values(3, 1);
insert into blog_article_tag(article_id, tag_id) values(3, 4);
insert into blog_article_tag(article_id, tag_id) values(4, 23);
insert into blog_article_tag(article_id, tag_id) values(4, 26);
insert into blog_article_tag(article_id, tag_id) values(4, 27);
insert into blog_article_tag(article_id, tag_id) values(4, 28);
insert into blog_article_tag(article_id, tag_id) values(5, 1);
insert into blog_article_tag(article_id, tag_id) values(5, 2);
insert into blog_article_tag(article_id, tag_id) values(5, 4);
insert into blog_article_tag(article_id, tag_id) values(5, 6);
insert into blog_article_tag(article_id, tag_id) values(6, 18);
insert into blog_article_tag(article_id, tag_id) values(6, 24);
insert into blog_article_tag(article_id, tag_id) values(6, 14);
insert into blog_article_tag(article_id, tag_id) values(6, 6);
insert into blog_article_tag(article_id, tag_id) values(7, 21);
insert into blog_article_tag(article_id, tag_id) values(7, 22);
insert into blog_article_tag(article_id, tag_id) values(7, 4);
insert into blog_article_tag(article_id, tag_id) values(7, 6);
insert into blog_article_tag(article_id, tag_id) values(8, 1);
insert into blog_article_tag(article_id, tag_id) values(8, 17);
insert into blog_article_tag(article_id, tag_id) values(8, 19);
insert into blog_article_tag(article_id, tag_id) values(8, 16);
insert into blog_article_tag(article_id, tag_id) values(9, 11);
insert into blog_article_tag(article_id, tag_id) values(9, 21);
insert into blog_article_tag(article_id, tag_id) values(9, 14);

insert into blog_article_tag(article_id, tag_id) values(10, 1);
insert into blog_article_tag(article_id, tag_id) values(10, 17);
insert into blog_article_tag(article_id, tag_id) values(11, 19);
insert into blog_article_tag(article_id, tag_id) values(12, 16);
insert into blog_article_tag(article_id, tag_id) values(13, 11);
insert into blog_article_tag(article_id, tag_id) values(13, 21);
insert into blog_article_tag(article_id, tag_id) values(13, 14);

insert into blog_article_tag(article_id, tag_id) values(14, 1);
insert into blog_article_tag(article_id, tag_id) values(15, 17);
insert into blog_article_tag(article_id, tag_id) values(16, 19);
insert into blog_article_tag(article_id, tag_id) values(16, 16);
insert into blog_article_tag(article_id, tag_id) values(16, 11);
insert into blog_article_tag(article_id, tag_id) values(16, 21);
insert into blog_article_tag(article_id, tag_id) values(17, 14);

insert into blog_article_tag(article_id, tag_id) values(17, 1);
insert into blog_article_tag(article_id, tag_id) values(18, 17);
insert into blog_article_tag(article_id, tag_id) values(18, 19);
insert into blog_article_tag(article_id, tag_id) values(18, 16);
insert into blog_article_tag(article_id, tag_id) values(19, 11);
insert into blog_article_tag(article_id, tag_id) values(19, 21);
insert into blog_article_tag(article_id, tag_id) values(19, 14);

insert into blog_article_tag(article_id, tag_id) values(20, 1);
insert into blog_article_tag(article_id, tag_id) values(20, 17);
insert into blog_article_tag(article_id, tag_id) values(20, 19);
insert into blog_article_tag(article_id, tag_id) values(20, 16);
insert into blog_article_tag(article_id, tag_id) values(20, 11);
insert into blog_article_tag(article_id, tag_id) values(21, 21);
insert into blog_article_tag(article_id, tag_id) values(21, 14);

insert into blog_article_tag(article_id, tag_id) values(21, 1);
insert into blog_article_tag(article_id, tag_id) values(21, 17);
insert into blog_article_tag(article_id, tag_id) values(21, 19);
insert into blog_article_tag(article_id, tag_id) values(22, 16);
insert into blog_article_tag(article_id, tag_id) values(22, 11);
insert into blog_article_tag(article_id, tag_id) values(22, 21);
insert into blog_article_tag(article_id, tag_id) values(22, 14);

insert into blog_article_tag(article_id, tag_id) values(22, 1);
insert into blog_article_tag(article_id, tag_id) values(23, 17);
insert into blog_article_tag(article_id, tag_id) values(23, 19);
insert into blog_article_tag(article_id, tag_id) values(23, 16);
insert into blog_article_tag(article_id, tag_id) values(23, 11);
insert into blog_article_tag(article_id, tag_id) values(23, 21);
