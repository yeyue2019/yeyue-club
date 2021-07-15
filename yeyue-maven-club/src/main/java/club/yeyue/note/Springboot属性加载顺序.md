### springboot多环境配置:

application.properties中添加配置：

spring.profiles.active=?

根据不同环境创建配置文件:

application-?.properties

```
example:
application-dev.properties：开发环境
application-test.properties：测试环境
application-prod.properties：生产环境
```

***

### 加载顺序

根据优先级排序：

```
1. 命令行传入的参数 ex: --server.port=8090
2. SPRING_APPLICATION_JSON中的属性。SPRING_APPLICATION_JSON是以JSON格式配置在系统环境变量中的内容
3. java:comp/env中的JNDI属性
4. Java的系统属性，可以通过System.getProperties()获得的内容
5. 操作系统的环境变量
6. 通过random.*配置的随机属性
7. 位于当前应用jar包之外，针对不同{profile}环境的配置文件内容，例如：application-{profile}.properties或是YAML定义的配置文件
8. 位于当前应用jar包之内，针对不同{profile}环境的配置文件内容，例如：application-{profile}.properties或是YAML定义的配置文件
9. 位于当前应用jar包之外的application.properties和YAML配置内容
10.位于当前应用jar包之内的application.properties和YAML配置内容
11.在@Configuration注解修改的类中，通过@PropertySource注解定义的属性
12.应用默认属性，使用SpringApplication.setDefaultProperties定义的内容
```