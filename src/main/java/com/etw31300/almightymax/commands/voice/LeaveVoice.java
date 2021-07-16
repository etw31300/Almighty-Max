package com.etw31300.almightymax.commands.voice;

import com.etw31300.almightymax.commands.BaseCommand;
import com.etw31300.almightymax.commands.annotations.Command;
import discord4j.common.util.Snowflake;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.VoiceChannel;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

@Command(keyword = "leave")
public class LeaveVoice extends BaseCommand {
    @Value("${almightymax.uid}")
    private String maxUid;

    @Override
    public Mono<Void> execute(Message commandMessage) {
        return Mono.just(commandMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getAuthorAsMember)
                .flatMap(Member::getVoiceState)
                .flatMap(VoiceState::getChannel)
                .flatMap(VoiceChannel::sendDisconnectVoiceState)
                .then();
    }
}
