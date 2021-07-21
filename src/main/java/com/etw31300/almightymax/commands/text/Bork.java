package com.etw31300.almightymax.commands.text;

import com.etw31300.almightymax.commands.BaseCommand;
import com.etw31300.almightymax.commands.annotations.Command;
import com.etw31300.almightymax.helpers.embed.EmbeddedMaxMessage;
import discord4j.core.object.Embed;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Log4j2
@Command(keyword = "bork", aliases = {"bark"})
public class Bork extends BaseCommand {

    @Override
    public Mono<Void> execute(Message commandMessage) {
        return Mono.just(commandMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(Message::getChannel)
                .flatMap(
                        messageChannel -> messageChannel.createMessage("Bork Bork!")
                ).then();
    }
}
