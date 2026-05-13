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
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uk_lms_user_user_name ON lms_user(user_name);
