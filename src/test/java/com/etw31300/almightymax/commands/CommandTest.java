package com.etw31300.almightymax.commands;

import com.etw31300.almightymax.eventlisteners.message.MessageListener;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Message;
import discord4j.discordjson.json.MessageData;
import discord4j.discordjson.json.UserData;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

@Log4j2
public class CommandTest {

    private long botUid = 345646168933531658L;
    private long userUid = 282771692319932418L;
    private long debugChannelUid = 394293538390671360L;

    @Test
    public void isCommand_test() {
        GatewayDiscordClient client = createDiscordClient();
        MessageData goodMessage = MessageData.builder()
                .content("m!bork")
                .channelId(debugChannelUid)
                .author(UserData.builder()
                        .id(userUid)
                        .build())
                .build();
        Message testMsg = new Message(client, goodMessage);
        TestListener listener = new TestListener();
    }

    @Test
    public void bork_test() {

    }

    private GatewayDiscordClient createDiscordClient() {
        return DiscordClientBuilder.create("${almightymax.token}").build().login().block();
    }

    @NoArgsConstructor
    private static class TestListener extends MessageListener {
    }
}
