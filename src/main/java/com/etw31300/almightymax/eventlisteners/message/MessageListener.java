package com.etw31300.almightymax.eventlisteners.message;

import com.etw31300.almightymax.commands.CommandRegistry;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Log4j2
public abstract class MessageListener {
    @Value("${almightymax.commandPrefix}")
    private String prefix;

    /**
     * Processes the event message to determine if it is attempting to invoke a command in the CommandRegistry
     * @param eventMessage
     * @return
     */
    public Mono<Void> processMessage(Message eventMessage) {
        CommandRegistry[] commandRegistry = CommandRegistry.values();

        try {
            // If the user is a bot, ignore the message.
            if(eventMessage.getAuthor().map(User::isBot).orElse(false))
                return Mono.just(eventMessage).then();

            // Check all of the commands in the CommandRegistry
            for(CommandRegistry command : commandRegistry) {
                String messageContent = eventMessage.getContent();
                // If the message starts with the prefix and either the keyword or any of the associated aliases, execute the command's runner method.
                if (messageContent.startsWith(prefix + command.getCommandKeyword())
                        || Arrays.stream(command.getCommandAliases()).anyMatch(alias -> messageContent.startsWith(prefix + alias))) {
                    return command.getCommandObject().execute(eventMessage);
                }
            }
        }  catch(Exception error) {
            log.error("Error processing message -> {}", error.getMessage());
        }
        return Mono.just(eventMessage).then();
    }
}
