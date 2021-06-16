package com.etw31300.almightymax.eventlisteners.message;

import com.etw31300.almightymax.eventlisteners.EventListener;
import discord4j.core.event.domain.message.MessageCreateEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class MessageCreateListener extends MessageListener implements EventListener<MessageCreateEvent> {
    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return processMessage(event.getMessage());
    }

    @Override
    public Mono<Void> handleError(Throwable error) {
        log.error("Error handling MessageCreateListener -> {}", error.getMessage());
        return Mono.just(error).then();
    }
}
