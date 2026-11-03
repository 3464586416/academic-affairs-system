# 校园教务管理系统

> 多模块教务后台：开课排课、选课、考试、成绩（Excel 报表）、教室借用、院系/学期/用户权限，服务端渲染 + RBAC 权限 + AOP 操作审计。
>
> **技术栈**：Java 17 · Spring Boot 3.2 · Spring MVC · Spring Security · MyBatis-Plus · Thymeleaf · MySQL · Apache POI · Lombok

## 功能模块

| 模块 | 说明 |
|---|---|
| 登录与权限 | BCrypt 密码哈希（Spring Security crypto）+ 自定义拦截器按 管理员 / 教师 / 教务秘书 三种角色做 URL 级授权（粒度实现见 interceptor/AuthInterceptor.java）；教师仅可成绩录入、排课/考试保存与本人资料修改，越权一律 403 |
| 开课与排课 | 学期 → 课程 → 班级 → 课表（教师、教室、时间段冲突校验） |
| 选课 | 学生选课 / 退选，容量控制 |
| 考试与成绩 | 考试安排、成绩录入、**Apache POI 成绩报表导出** |
| 教室借用 | 教室资源管理与借用申请 |
| 系统管理 | 院系 / 专业 / 用户维护，**AOP 统一操作日志**（自定义 `@OperationLog` 注解 + 切面落库） |

## 架构与分层

```
controller(15) → service → mapper(MyBatis-Plus) → MySQL 25 表
      ↑
common: Result / PageResult / QueryHelper —— 统一返回、分页、动态条件查询
config: SecurityConfig / MybatisPlusConfig / WebConfig / MyMetaObjectHandler(自动填充)
aspect: OperationLogAspect —— 注解式审计
view:   Thymeleaf 17 页（后台管理界面）
```

工程规模：126 个 Java 文件、约 3500 行、25 张数据表。

## 快速开始

```bash
# 1. 建库并导入结构与演示数据
mysql -uroot -p -e "CREATE DATABASE School_System DEFAULT CHARSET utf8mb4"
mysql -uroot -p School_System < sql/school_system.sql

# 2. 配置数据库账号（环境变量，勿写进代码）
set DB_USER=root
set DB_PASSWORD=你的MySQL密码

# 3. 运行
mvnw.cmd spring-boot:run
```

浏览器打开 http://localhost:8080 ，默认账号 `admin / 123456`（管理员）、`zhulin / 123456`（教务秘书）、`zhangwei / 123456`（教师），库内均为 BCrypt 哈希存储；其余演示数据见 `sql/school_system.sql`。

云端数据库（如 TiDB Cloud 等 MySQL 兼容服务）直接覆盖 `DB_URL` 即可运行，代码与配置中不含任何真实凭据。

## 目录结构

```
com.city.springboot_big_schoolwork
├── annotation/  OperationLog（自定义审计注解）
├── aspect/      OperationLogAspect
├── common/      Result / PageResult / QueryHelper
├── config/      Security / MybatisPlus / Web / MetaObjectHandler
├── controller/  15 个（课程/排课/选课/考试/成绩/教室/学生/教师/系统管理…）
├── entity/  mapper/  service/  interceptor/
└── resources/templates/  Thymeleaf 页面
```

## 说明

- 本系统为《Spring Boot 框架开发》课程大作业，学生 / 教师 / 手机号等演示数据均为虚构。
- 数据库连接通过 `DB_URL` / `DB_USER` / `DB_PASSWORD` 环境变量注入，`PORT` 可覆盖服务端口。
