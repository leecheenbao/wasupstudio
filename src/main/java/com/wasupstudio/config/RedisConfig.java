package com.wasupstudio.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Slf4j
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String pwd;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        log.info("redis連線資訊 {} {}", host, port);
        redisConfig.setHostName(host);
        redisConfig.setPort(port);
        // 如果Redis服务器有密码，请设置密码
        redisConfig.setPassword("1rk5u38fswetercc");

        return new LettuceConnectionFactory(redisConfig);
    }
}

