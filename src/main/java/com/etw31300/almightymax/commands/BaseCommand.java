package com.etw31300.almightymax.commands;

import discord4j.core.object.entity.Message;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

public abstract class BaseCommand implements CommandRunner {
    @Override
    public Mono<Void> execute(Message message) {
        return Mono.just(message).then();
    }
}
