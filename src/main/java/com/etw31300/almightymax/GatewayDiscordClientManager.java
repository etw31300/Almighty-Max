package com.etw31300.almightymax;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GatewayDiscordClientManager {
    @Value("${almightymax.token}")
    private static String token;

    public static final GatewayDiscordClient CLIENT;

    static {
        CLIENT = DiscordClientBuilder.create(token)
                .build()
                .login()
                .block();
    }
}
