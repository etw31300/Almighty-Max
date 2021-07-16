package com.etw31300.almightymax.commands.music;

import com.etw31300.almightymax.commands.BaseCommand;
import com.etw31300.almightymax.commands.annotations.Command;
import com.etw31300.almightymax.lavaplayer.manager.GuildPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.event.domain.VoiceStateUpdateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.voice.AudioProvider;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static com.etw31300.almightymax.lavaplayer.manager.LavaPlayerManager.PLAYER_MANAGER;

@Log4j2
@Command(keyword = "play")
public class PlayMusic extends BaseCommand {

    @Override
    public Mono<Void> execute(Message commandMessage) {

        //remove the first part of the message (the command).
        String audioSearch = commandMessage.getContent().split("\\S+\\s+")[1];

        //TODO: Parse the remaining string for a URL and perform Youtube search if it does not parse.

        final VoiceChannel voiceChannel = Mono.just(commandMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getAuthorAsMember)
                .flatMap(Member::getVoiceState)
                .flatMap(VoiceState::getChannel)
                .block();

        if (voiceChannel == null) {
            log.warn("Voice Channel came back as null for playback");
            return Mono.just(commandMessage).then();
        }

        final GuildPlayerManager manager = GuildPlayerManager.of(voiceChannel.getGuildId());
        final AudioProvider provider = manager.getProvider();
        final Mono<Void> connection = voiceChannel.join(spec -> spec.setProvider(provider))
                .flatMap(voiceConnection -> {
                    // voice state indicating whether the bot is alone (1)
                    final Publisher<Boolean> voiceStateCounter = voiceChannel.getVoiceStates()
                            .count()
                            .map(count -> 1L == count);

                    // checks if bot is alone after 10 seconds.
                    final Mono<Void> onDelay = Mono.delay(Duration.ofSeconds(10L))
                            .filterWhen(ignored -> voiceStateCounter)
                            .switchIfEmpty(Mono.never())
                            .then();

                    // Check if the bot is alone as people leave.
                    final Mono<Void> onEvent = voiceChannel.getClient().getEventDispatcher().on(VoiceStateUpdateEvent.class)
                            .filter(event -> event.getOld().flatMap(VoiceState::getChannelId).map(voiceChannel.getId()::equals).orElse(false))
                            .filterWhen(ignored -> voiceStateCounter)
                            .next()
                            .then();

                    return Mono.first(onDelay, onEvent).then(voiceConnection.disconnect());
                });

        PLAYER_MANAGER.loadItemOrdered(manager, audioSearch, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                log.info("Track Loaded -> {}", track.getIdentifier());
                // queue the track for playback
                manager.getScheduler().play(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                log.info("Playlist Loaded -> {}", playlist.getName());
            }

            @Override
            public void noMatches() {
                log.warn("No matching audio tracks were found.");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                log.error("Audio Track loading failed with the following exception -> {}", exception.getMessage(), exception);
            }
        });

        return connection;
    }
}
