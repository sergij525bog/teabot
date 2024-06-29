package com.example.teabot.model;

import com.example.teabot.TeaBot;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessages;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatHandler {
    private static final Map<Long, List<Integer>> memberMessages = new HashMap<>();
    private static final Map<Long, Long> memberChat = new HashMap<>();
    private static final Set<Long> chatToDelete = new HashSet<>();

    public static void markChatToDelete(Long senderId) {
        chatToDelete.add(senderId);
    }

    @SneakyThrows
    public static void renderMessage(Long senderId, String text, ReplyKeyboard markup, TeaBot bot) {
        final SendMessage message = buildMessage(senderId, text, markup);

        final Integer messageId = bot.execute(message).getMessageId();
        storeMessage(senderId, messageId);
    }

    private static SendMessage buildMessage(Long senderId, String text, ReplyKeyboard markup) {
        final SendMessage message = SendMessage.builder()
                .chatId(getChatId(senderId))
                .text(text)
                .replyMarkup(markup)
                .build();

        Optional.ofNullable(getSenderMessages(senderId))
                .filter(ids -> !ids.isEmpty())
                .map(ids -> ids.get(ids.size() - 1))
                .ifPresent(message::setReplyToMessageId);

        return message;
    }

    private static void storeMessage(Long senderId, Integer messageId) {
        getSenderMessages(senderId).add(messageId);
    }

    @SneakyThrows
    public static void clearChatMessages(Long senderId, TeaBot bot) {
        List<Integer> messageIds = getSenderMessages(senderId);

        DeleteMessages messages = DeleteMessages.builder()
                .chatId(getChatId(senderId))
                .messageIds(messageIds)
                .build();
        messageIds.clear();

        bot.execute(messages);
    }

    public static boolean chatShouldBeDeleted(Long senderId) {
        return chatToDelete.contains(senderId);
    }

    public static void clearChatInfo(Long senderId) {
        memberMessages.remove(senderId);
        memberChat.remove(senderId);
        chatToDelete.remove(senderId);
    }

    public static void storeChatInfo(UpdateParser parser) {
        final Long memberId = parser.getSenderId();

        memberChat.putIfAbsent(memberId, parser.getChatId());
        memberMessages.putIfAbsent(memberId, new ArrayList<>());

        storeMessage(memberId, parser.getMessageId());
    }

    private static Long getChatId(Long senderId) {
        return memberChat.get(senderId);
    }

    private static List<Integer> getSenderMessages(Long senderId) {
        return memberMessages.get(senderId);
    }
}
