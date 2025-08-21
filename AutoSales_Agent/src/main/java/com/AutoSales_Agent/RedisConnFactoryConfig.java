package com.AutoSales_Agent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConnFactoryConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(
            @Value("${spring.redis.host:redis}") String host,
            @Value("${spring.redis.port:6379}") int port){

        RedisStandaloneConfiguration cfg = new RedisStandaloneConfiguration(host, port);
        
        return new LettuceConnectionFactory(cfg);
    }
    @Bean
    ApplicationRunner redisPingRunner(StringRedisTemplate srt) {
        return args -> {
            try {
                srt.opsForValue().set("health:ping", "ok");
                String v = srt.opsForValue().get("health:ping");
                System.out.println("[Redis] Connected. value=" + v);
            } catch (Exception e) {
                System.err.println("[Redis] CONNECT FAIL -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        };
    }
}
