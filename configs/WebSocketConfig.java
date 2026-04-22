package com.chatapp.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // The endpoint where clients connect
        registry.addEndpoint("/chat")
                .setAllowedOrigins(AppConstants.FRONT_END_URL)
                .withSockJS();

        // chat endpoint par websocket tcp connection establish hoga
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Prefix for messages sent FROM server TO client
        config.enableSimpleBroker("/topic");

        // Prefix for messages sent FROM client TO server
        config.setApplicationDestinationPrefixes("/app");
    }
}
