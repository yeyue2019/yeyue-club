package club.yeyue.maven.redis.config;

import club.yeyue.maven.redis.jedis.JedisService;
import club.yeyue.maven.redis.lettuce.LettuceService;
import club.yeyue.maven.redis.redisson.RedissonService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 调整redisTemplate的序列化方式
 *
 * @author fred
 * @date 2021-05-12 13:32
 */
@Configuration
public class RedisConfiguration {

    // 根据工厂类型加载redisTemplate
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    // jedis时加载
    @Bean
    @ConditionalOnProperty(name = "spring.redis.client-type", havingValue = "jedis", matchIfMissing = true)
    public JedisService jedisService(RedisConnectionFactory factory) {
        return new JedisService((JedisConnectionFactory) factory);
    }

    // lettuce加载 暂时没有想到原生实现
    @Bean
    @ConditionalOnProperty(name = "spring.redis.client-type", havingValue = "lettuce", matchIfMissing = true)
    public LettuceService lettuceService(RedisConnectionFactory factory) {
        return new LettuceService();
    }

    /**
     * 总结:只要引入这个bean就会实例化redisson
     */
    @Bean
    @Lazy
    @ConditionalOnBean(RedissonClient.class)
    @ConditionalOnProperty(name = "spring.redis.client-type", havingValue = "", matchIfMissing = true)
    public RedissonService redissonService(RedissonClient redissonClient) {
        return new RedissonService(redissonClient);
    }

}
