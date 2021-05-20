# yeyue-club

***

**springboot自行搭建项目架子**

主要配置都是通过springboot自动配置启动的

使用log4j2收集系统日志

**目前集成中间件：**

> redis和redis三大客户端jedis、lettuce、redisson

> mysql、druid连接池、数据库框架jpa、mybatis-plus

注意：

1. redis可以根据配置项自动选择使用的客户端

2. mybatis-plus使用actable自动建表,且采用无xml开发的方式