package com.etw31300.almightymax.commands;

import com.etw31300.almightymax.commands.annotations.Command;
import com.etw31300.almightymax.commands.music.PauseMusic;
import com.etw31300.almightymax.commands.music.PlayMusic;
import com.etw31300.almightymax.commands.music.ResumeMusic;
import com.etw31300.almightymax.commands.music.SkipMusic;
import com.etw31300.almightymax.commands.text.Bork;
import com.etw31300.almightymax.commands.text.Joke;
import com.etw31300.almightymax.commands.voice.JoinVoice;
import com.etw31300.almightymax.commands.voice.LeaveVoice;

/**
 * Registers all of the command classes that are described with Command annotations and that extend the BaseCommand class.
 */
public enum CommandRegistry {
    /**
     * Text Commands
     */
    BORK(Bork.class, new Bork()),
    JOKE(Joke.class, new Joke()),

    /**
     * Voice Commands
     */
    JOIN_VOICE(JoinVoice.class, new JoinVoice()),
    LEAVE_VOICE(LeaveVoice.class, new LeaveVoice()),

    /**
     * Music Commands (Lavalink)
     */
    PLAY_MUSIC(PlayMusic.class, new PlayMusic()),
    SKIP_MUSIC(SkipMusic.class, new SkipMusic()),
    PAUSE_MUSIC(PauseMusic.class, new PauseMusic()),
    RESUME_MUSIC(ResumeMusic.class, new ResumeMusic());

    private Class<? extends BaseCommand> commandClass;
    private BaseCommand commandObject;

    private CommandRegistry(Class<? extends BaseCommand> commandClass, BaseCommand commandObject) {
        this.commandClass = commandClass;
        this.commandObject = commandObject;
    }

    public Class<? extends BaseCommand> getCommandClass() {
        return commandClass;
    }

    public String getCommandKeyword() {
        Command commandAnnotation = getCommandClass().getAnnotation(Command.class);
        return commandAnnotation.keyword();
    }

    public String[] getCommandAliases() {
        Command commandAnnotation = getCommandClass().getAnnotation(Command.class);
        return commandAnnotation.aliases();
    }

    public BaseCommand getCommandObject() {
        return commandObject;
    }
}
