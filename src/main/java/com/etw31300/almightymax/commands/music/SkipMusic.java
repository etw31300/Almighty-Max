package com.etw31300.almightymax.commands.music;

import com.etw31300.almightymax.commands.BaseCommand;
import com.etw31300.almightymax.commands.annotations.Command;
import com.etw31300.almightymax.lavaplayer.managers.GuildPlayerManager;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Command(keyword = "skip")
public class SkipMusic extends BaseCommand {
    @Override
    public Mono<Void> execute(Message commandMessage) {
        return Mono.just(commandMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getAuthorAsMember)
                .flatMap(Member::getVoiceState)
                .flatMap(VoiceState::getChannel)
                .flatMap(voiceChannel -> {
                    GuildPlayerManager manager = GuildPlayerManager.of(voiceChannel.getGuildId());
                    manager.getScheduler().skip();
                    return Mono.just(voiceChannel);
                })
                .then();
    }
}
