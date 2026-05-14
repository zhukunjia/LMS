# 图书管理系统
这是一个图书管理系统，技术栈有`Spring boot`、`mybatis-plus`、`caffeine`等，包含用户模块、图书模块。对于图书管理，提供了基于用户-权限组的权限控制模式。

## 项目特性
- 用户模块，提供新增用户、删除用户功能
- 提供用户登录、登出功能，全局`token`校验
- 图书管理模块，图书管理增删改查
- 基于用户-权限组对图书管理模块接口进行权限控制
- 基于H2 数据库，无需外部依赖。提供了内存模式和文件模式配置

## 技术栈
- 后端：`JDK1.8` `Spring boot` `caffeine` `lombok` `hutool` `swagger` `AOP`
- 数据库：`H2`
- 其他：`Maven` `Git`

## 环境依赖
- JDK1.8
- Git

## 快速安装&运行

### 1. 克隆项目
```bash
git clone https://github.com/zhukunjia/LMS.git
cd LMS
```
### 下载依赖
```bash
mvn clean package -D maven.test.skip=true
```

### 注意事项
- 请求头有两个，`x-lms-userId` 和 `x-lms-token`。除了登录接口之外，其他接口一律需要传请求头
- [表设计](./src/main/resources/db/schema-h2.sql), [初始化数据](./src/main/resources/db/data-h2.sql)
- 第一次启动，最好配置`spring.sql.init.mode=always`，之后再配置`spring.sql.init.mode=embedded`。`mode=embedded`时，配置`file`模式，不会执行sql初始化脚本；`file`模式如果要支持初始化脚本，需要配置`mode=always`
- 使用了`lombok`，如果在`IDEA` 里打开，需要装一下`lombok`插件
- 打开了H2控制台，直接访问 http://localhost:8080/h2-console 即可
- `swagger`地址是 http://localhost:8080/swagger-ui/index.html

## 设计文档
- [脑图](./docs/图书管理系统.png)
- 所有的设计文档都会放在[这里](./docs)