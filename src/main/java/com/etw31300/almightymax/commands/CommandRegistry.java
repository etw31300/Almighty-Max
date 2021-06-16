package com.etw31300.almightymax.commands;

import com.etw31300.almightymax.commands.annotations.Command;
import reactor.core.publisher.Mono;

/**
 * Registers all of the command classes that are described with Command annotations and that extend the BaseCommand class.
 */
public enum CommandRegistry {
    BORK(Bork.class, new Bork()), JOKE(Joke.class, new Joke());

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
