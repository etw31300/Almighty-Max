package com.etw31300.almightymax.commands;

import com.etw31300.almightymax.commands.interfaces.CommandRunner;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class BaseCommand implements CommandRunner {
    @Override
    public Mono<Void> execute(Message commandMessage) {
        return Mono.just(commandMessage).then();
    }
}
