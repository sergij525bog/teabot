package com.example.teabot.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.MaybeInaccessibleMessage;
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

    public static UpdateParser fromUpdate(Update update) {
        final UpdateParser parser = new UpdateParser();
        if (update.hasMessage()) {
            final Message message = update.getMessage();

            parser.chatId = message.getChatId();
            parser.data = message.getText();
            parser.senderId = message.getFrom().getId();
            parser.messageId = message.getMessageId();
        } else if (update.hasCallbackQuery()) {
            final CallbackQuery callbackQuery = update.getCallbackQuery();
            final MaybeInaccessibleMessage message = callbackQuery.getMessage();

            parser.chatId = message.getChatId();
            parser.data = callbackQuery.getData();
            parser.senderId = callbackQuery.getFrom().getId();
            parser.messageId = message.getMessageId();
        } else {
            throw new RuntimeException("Update does not have required data");
        }

        return parser;
    }
}
