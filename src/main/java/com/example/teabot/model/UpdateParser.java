package com.example.teabot.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class UpdateParser {
    private Long chatId;
    private Long senderId;
    private Integer messageId;
    private String data;

//    curl -X POST https://api.telegram.org/bot<bot-token>/sendMessage -H 'Content-Type: application/json' -d '{"chat_id": "<chatId>", "text": "ALERT! Root volume is about to be full"}'


    public static UpdateParser fromUpdate(Update update) {
        final UpdateParser parser = new UpdateParser();
        if (update.hasMessage()) {
            final Message message = update.getMessage();

            parser.chatId = message.getChatId();
            parser.data = message.getText();
            parser.senderId = message.getFrom().getId();
            parser.messageId = message.getMessageId();

            return parser;
        }

        throw new RuntimeException("Update does not have required data");
    }
}
