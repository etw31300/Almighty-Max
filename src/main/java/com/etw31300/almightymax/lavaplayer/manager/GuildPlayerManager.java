package com.etw31300.almightymax.lavaplayer.manager;

import com.etw31300.almightymax.lavaplayer.provider.LavaPlayerAudioProvider;
import com.etw31300.almightymax.lavaplayer.scheduler.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import discord4j.common.util.Snowflake;
import lombok.Data;;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.etw31300.almightymax.lavaplayer.manager.LavaPlayerManager.PLAYER_MANAGER;

@Data
public class GuildPlayerManager {
    private static final Map<Snowflake, GuildPlayerManager> MANAGERS = new ConcurrentHashMap<>();

    public static GuildPlayerManager of(final Snowflake id) {
        return MANAGERS.computeIfAbsent(id, ignored -> new GuildPlayerManager());
    }

    private final AudioPlayer player;
    private final TrackScheduler scheduler;
    private final LavaPlayerAudioProvider provider;

    private GuildPlayerManager() {
        player = PLAYER_MANAGER.createPlayer();
        scheduler = new TrackScheduler(player);
        provider = new LavaPlayerAudioProvider(player);

        player.addListener(scheduler);
    }
}
