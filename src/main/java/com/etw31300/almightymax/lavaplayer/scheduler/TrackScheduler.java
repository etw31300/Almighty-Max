package com.etw31300.almightymax.lavaplayer.scheduler;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import lombok.extern.log4j.Log4j2;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Log4j2
public class TrackScheduler extends AudioEventAdapter {
    private final List<AudioTrack> queue;
    private final AudioPlayer player;

    public TrackScheduler(AudioPlayer player) {
        this.queue = Collections.synchronizedList(new LinkedList<>());
        this.player = player;
    }

    public List<AudioTrack> getQueue() {
        return queue;
    }

    public boolean play(AudioTrack track) {
        return play(track, false);
    }

    public boolean play(AudioTrack track, boolean force) {
        boolean playing = player.startTrack(track, !force);

        if (!playing) {
            queue.add(track);
        }

        return playing;
    }

    public boolean skip() {
        return !queue.isEmpty() && play(queue.remove(0), true);
    }

    @Override
    public void onTrackEnd(final AudioPlayer player, final AudioTrack track, final AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            skip();
        }
    }
}