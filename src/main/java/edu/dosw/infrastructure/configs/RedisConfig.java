package edu.dosw.infrastructure.configs;

import edu.dosw.infrastructure.events.UserEventsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final UserEventsListener userEventsListener;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // Suscribirse al canal específico de eventos de login
        container.addMessageListener(
                new MessageListenerAdapter(userEventsListener),
                ChannelTopic.of("events.login.success")
        );

        // También puedes suscribirte a otros canales si es necesario
        container.addMessageListener(
                new MessageListenerAdapter(userEventsListener),
                ChannelTopic.of("events.user.created")
        );

        return container;
    }
}