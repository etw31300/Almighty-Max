package com.etw31300.almightymax.eventlisteners;

import discord4j.core.event.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

public interface EventListener<T extends Event> {
    Logger LOGGER = LoggerFactory.getLogger(EventListener.class);

    Class<T> getEventType();
    Mono<Void> execute(T event);

    default Mono<Void> handleError(Throwable error) {
        LOGGER.error("Unable to process " + getEventType().getSimpleName(), error);
        return Mono.just(error).then();
    }
}
