package com.etw31300.almightymax.helpers.embed;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.Embed;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.discordjson.json.EmbedAuthorData;
import discord4j.discordjson.json.EmbedData;
import discord4j.discordjson.json.EmbedThumbnailData;
import discord4j.rest.util.Color;

import java.time.Instant;
import java.util.function.Consumer;


public class EmbeddedMaxMessage{
    public static Consumer<EmbedCreateSpec> CreateEmbeddedMaxMessage(GatewayDiscordClient client, String message) {
        User clientUser = client.getSelf().block();

        if (clientUser == null)
            return null;

        Consumer<EmbedCreateSpec> spec = embedCreateSpec -> embedCreateSpec.setThumbnail(clientUser.getAvatarUrl())
                .setColor(Color.of(109, 85, 68))
                .setAuthor(clientUser.getUsername(), clientUser.getAvatarUrl(), clientUser.getAvatarUrl())
                .setTimestamp(Instant.now())
                .setDescription(message);

        return spec;
    }
}
