package com.etw31300.almightymax.eventlisteners.message;

import com.etw31300.almightymax.eventlisteners.EventListener;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class MessageUpdateListener extends MessageListener implements EventListener<MessageUpdateEvent> {

    @Override
    public Class<MessageUpdateEvent> getEventType() {
        return MessageUpdateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageUpdateEvent event) {
        return Mono.just(event).then();
    }

    @Override
    public Mono<Void> handleError(Throwable error) {
        log.error("Error handling MessageUpdateListener -> {}", error.getMessage());
        return null;
    }
}
