DROP TABLE IF EXISTS `lms_user`;

CREATE TABLE `lms_user`
(
    id varchar(30) not null COMMENT '主键ID',
    user_name varchar(30) not null COMMENT '用户名',
    email varchar(50) null default null COMMENT '邮箱',
    phone varchar(20) null default null COMMENT '手机号码',
    person_name varchar(30) null default null COMMENT '姓名',
    password varchar(64) not null COMMENT '登录密码',
    create_by varchar(30) not null COMMENT '创建人',
    create_time datetime not null default CURRENT_TIMESTAMP COMMENT '创建时间',
    last_modified_by varchar(30) not null COMMENT '最后修改人',
    last_modified_time datetime not null default CURRENT_TIMESTAMP COMMENT '最后修改时间',
    UNIQUE KEY uk_lms_user_user_name (user_name),
    PRIMARY KEY (id)
);

--CREATE UNIQUE INDEX uk_lms_user_user_name ON lms_user(user_name);

DROP TABLE IF EXISTS `lms_library`;

 CREATE TABLE `lms_library`
 (
     id varchar(30) not null COMMENT '主键ID',
     name varchar(100) not null COMMENT '书名',
     isbn varchar(40) null default null COMMENT '书号',
     author varchar(50) null default null COMMENT '作者',
     publisher varchar(50) null default null COMMENT '出版社',
     publish_time date default null COMMENT '出版时间',
     category_name varchar(50) null default null COMMENT '图书分类',
     price decimal(10,2) default 0 COMMENT '图书价格',
     cover varchar(200) default null COMMENT '图书封面，可以存文档云的一个文件路径',
     status tinyint not null default 1 COMMENT '状态 0-下架 1-正常',
     create_by varchar(30) not null COMMENT '创建人',
     create_time datetime not null default CURRENT_TIMESTAMP COMMENT '创建时间',
     last_modified_by varchar(30) not null COMMENT '最后修改人',
     last_modified_time datetime not null default CURRENT_TIMESTAMP COMMENT '最后修改时间',
     UNIQUE KEY uk_lms_library_isbn (isbn),
     PRIMARY KEY (id)
 );
