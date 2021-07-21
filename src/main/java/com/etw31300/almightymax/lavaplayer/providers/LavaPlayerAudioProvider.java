package com.etw31300.almightymax.lavaplayer.providers;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import discord4j.voice.AudioProvider;

import java.nio.ByteBuffer;

public class LavaPlayerAudioProvider extends AudioProvider {

    private final AudioPlayer player;
    private final MutableAudioFrame frame = new MutableAudioFrame();

    public LavaPlayerAudioProvider(AudioPlayer player) {
        super(
                ByteBuffer.allocate(
                        StandardAudioDataFormats.DISCORD_OPUS.maximumChunkSize()
                )
        );

        frame.setBuffer(getBuffer());
        this.player = player;
    }

    public AudioPlayer getPlayer() {
        return this.player;
    }

    public MutableAudioFrame getFrame() {
        return this.frame;
    }

    @Override
    public boolean provide() {
        final boolean didProvide = player.provide(frame);

        if (didProvide)
            getBuffer().flip();

        return didProvide;
    }
}
