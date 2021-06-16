package com.etw31300.almightymax.commands;

import com.etw31300.almightymax.commands.annotations.Command;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Command(keyword = "joke")
public class Joke extends BaseCommand {

    public Mono<Void> execute(Message commandMessage) {

        return Mono.just(commandMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getChannel)
                .flatMap(messageChannel ->  messageChannel.createMessage("What happened to the dog that crossed the road?"))
                .delayElement(Duration.ofSeconds(2L))
                .flatMap(Message::getChannel)
                .flatMap(messageChannel -> messageChannel.createMessage("It got ran over! :laughing:"))
                .then();
    }
}
