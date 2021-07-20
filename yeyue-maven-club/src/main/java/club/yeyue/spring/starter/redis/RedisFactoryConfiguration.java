//package club.yeyue.spring.starter.redis;
//
//import org.redisson.spring.starter.RedissonAutoConfiguration;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//
///**
// * @author fred
// * @date 2021-05-13 10:50
// */
//@Configuration
//@AutoConfigureBefore(RedissonAutoConfiguration.class)
//@Import(RedisAutoConfiguration.class)
//public class RedisFactoryConfiguration {
//    // TODO: 2021/5/13 添加该配置的目的是强行让redisAutoConfiguration加载在RedissonAutoConfiguration之前:保证redis的三种连接工厂可以按照配置加载 而不是原先的按照 redisson 高于 lettuce和jedis
//}
