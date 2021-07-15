# yeyue-club

***

**springboot自行搭建项目架子**

> springboot自动配置

> log4j2收集系统日志

> websocket基本实现:tomcat,spring,spring-stomp

**目前集成中间件：**

> redis和redis三大客户端jedis、lettuce、redisson

> mysql、druid连接池、数据库框架jpa、mybatis-plus

**工具类**

> Jackson JSON转化

> 雪花Id生成算法

> Http请求工具 使用okhttp封装

> 加解密池工具类

注意：

1. redis可以根据配置项自动选择使用的客户端

2. mybatis-plus使用actable自动建表,且采用无xml开发的方式

3. spring stomp 实现了类似订阅的点对点和主题模式