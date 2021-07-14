package etocrm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisListenerConfig {
    @Autowired
    private RedisKeyExpirationListener redisKeyExpirationListener;
    @Value("${spring.redis.database}")
    private String redisDataBase;
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(redisKeyExpirationListener,new PatternTopic("__"+"keyspace@"+redisDataBase+"__"+"*" + ""));
        return container;
    }
}