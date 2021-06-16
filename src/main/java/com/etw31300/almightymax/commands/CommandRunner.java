package com.etw31300.almightymax.commands;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public interface CommandRunner {
    Mono<Void> execute(Message message);
}
