package com.etw31300.almightymax.commands.music;

import com.etw31300.almightymax.commands.BaseCommand;
import com.etw31300.almightymax.commands.annotations.Command;
import com.etw31300.almightymax.lavaplayer.managers.GuildPlayerManager;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

@Command(keyword = "resume")
public class ResumeMusic extends BaseCommand {
    @Override
    public Mono<Void> execute(Message commandMessage) {
        return Mono.just(commandMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getAuthorAsMember)
                .flatMap(Member::getVoiceState)
                .flatMap(VoiceState::getChannel)
                .flatMap(voiceChannel -> {
                    GuildPlayerManager manager = GuildPlayerManager.of(voiceChannel.getGuildId());
                    manager.getPlayer().setPaused(false);
                    return Mono.just(voiceChannel);
                })
                .then();
    }
}
