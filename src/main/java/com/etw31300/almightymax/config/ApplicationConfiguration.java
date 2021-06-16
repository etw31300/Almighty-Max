package com.etw31300.almightymax.config;

import com.etw31300.almightymax.eventlisteners.EventListener;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Log4j2
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationConfiguration {

    @Value("${almightymax.token}")
    private String token;

    @Bean("discordClient")
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners) {
        GatewayDiscordClient client = null;

        try {
            log.info("Starting Almighty Max");
            client = DiscordClientBuilder.create(token)
                    .build()
                    .login()
                    .block();
            log.info("Max has successfully connected!");

            if (client != null) {
                for(EventListener<T> listener : eventListeners) {
                    client.on(listener.getEventType())
                            .flatMap(listener::execute)
                            .onErrorResume(listener::handleError)
                            .subscribe();
                }
            }
        }
        catch (Exception error) {
            log.error(error.getMessage());
        }
        return client;
    }
}
