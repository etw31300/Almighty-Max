package com.etw31300.almightymax.commands;

import com.etw31300.almightymax.commands.annotations.Command;
import discord4j.core.object.entity.Message;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Command(keyword = "bork", aliases = {"bark"})
public class Bork extends BaseCommand {

    @Override
    public Mono<Void> execute(Message commandMessage) {
        log.info("Executing Bork Command.");
        return Mono.just(commandMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getChannel)
                .flatMap(
                        messageChannel -> messageChannel.createMessage("Bork Bork!")
                ).then();
    }
}
