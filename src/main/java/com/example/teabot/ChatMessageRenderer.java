package com.example.teabot;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.UpdateParser;
import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.handlers.*;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessages;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.*;

public class ChatMessageRenderer {
    private final TeaBot bot;

    public ChatMessageRenderer(TeaBot bot) {
        this.bot = bot;
    }

    private static final Map<Long, ChatInfo> memberOrderInfo = new HashMap<>();
    private static final Map<Long, Long> memberChat = new HashMap<>();
    private static final Map<Long, AttributeHandler> lastWorkedHandler = new HashMap<>();
    private static final Set<Long> chatToClear = new HashSet<>();

    public void handle(Update update) {
        UpdateParser parser = UpdateParser.fromUpdate(update);
        storeChatInfo(parser);
        processUpdate(parser);
    }

    private void processUpdate(UpdateParser parser) {
        Long senderId = parser.getSenderId();

        OrderState currentState = getCurrentState(senderId);
        OrderState newState = handleByState(senderId, parser.getData());
        processNewState(senderId, currentState, newState);

        renderMessage(senderId);
        if (chatToClear.contains(senderId)) {
            clearChatInfo(senderId);
        }
    }

    private void processNewState(Long senderId, OrderState currentState, OrderState newState) {
        if (newState == OrderState.START) {
            chatToClear.add(senderId);
            return;
        }
        if (newState == OrderState.ERROR) {
            lastWorkedHandler.put(senderId, getHandler(senderId, currentState));
        }
        setCurrentState(senderId, newState);
        if (OrderState.isFinalState(newState)) {
            clearChatMessages(senderId);
            chatToClear.add(senderId);
        }
    }

    private OrderState handleByState(Long senderId, String data) {
        OrderState currentState = getCurrentState(senderId);
        AttributeHandler handler = getHandler(senderId, data, currentState);

        if (handler != null) {
            return handler.processUserInput(data, getChatInfo(senderId));
        }

        throw new NullPointerException("There is no actions for state " + currentState);
    }

    private AttributeHandler getHandler(Long senderId, String data, OrderState state) {
        if (data != null && NavigationButtons.isNavigation(data)) {
            return new NavigationHandler();
        }
        return getHandler(senderId, state);
    }

    private AttributeHandler getHandler(Long senderId, OrderState state) {
        if (state != OrderState.ERROR) {
            if (state == OrderState.SAVE_ORDER_AWAITING) {
                return new OrderSavingHandler(getChatInfo(senderId));
            }
            return HandlerFactory.getHandlerByState(state);
        }

        AttributeHandler prevHandler = lastWorkedHandler.get(senderId);
        return new ErrorHandler(prevHandler);
    }

    @SneakyThrows
    private void clearChatMessages(Long senderId) {
        List<Integer> messageIds = getChatInfo(senderId).getMessageIds();
        DeleteMessages messages = DeleteMessages.builder()
                .chatId(getChatId(senderId))
                .messageIds(messageIds)
                .build();
        messageIds.clear();

        bot.execute(messages);
    }

    private void clearChatInfo(Long senderId) {
        memberOrderInfo.remove(senderId);
        memberChat.remove(senderId);
        chatToClear.remove(senderId);
    }

    private static void storeChatInfo(UpdateParser parser) {
        Long senderId = parser.getSenderId();
        memberChat.putIfAbsent(senderId, parser.getChatId());
        memberOrderInfo.putIfAbsent(senderId, new ChatInfo());
        storeMessage(senderId, parser.getMessageId());
    }

    @SneakyThrows
    private void renderMessage(Long senderId) {
        final OrderState currentState = getCurrentState(senderId);
        if (currentState != OrderState.START) {
            final AttributeHandler handler = getHandler(senderId, currentState);

            final SendMessage message = buildMessage(senderId, handler);

            final Integer messageId = bot.execute(message).getMessageId();
            storeMessage(senderId, messageId);
        }
    }

    private static SendMessage buildMessage(Long senderId, AttributeHandler handler) {
        final SendMessage message = SendMessage.builder()
                .chatId(getChatId(senderId))
                .text(handler.question())
                .replyMarkup(handler.getMarkup())
                .build();

        Optional.ofNullable(getChatInfo(senderId))
                .map(ChatInfo::getMessageIds)
                .filter(ids -> !ids.isEmpty())
                .map(ids -> ids.get(ids.size() - 1))
                .ifPresent(message::setReplyToMessageId);

        return message;
    }

    private static void storeMessage(Long senderId, Integer messageId) {
        getChatInfo(senderId)
                .getMessageIds()
                .add(messageId);
    }

    private static Long getChatId(Long senderId) {
        return memberChat.get(senderId);
    }

    private static void setCurrentState(Long senderId, OrderState currentState) {
        getChatInfo(senderId).setCurrentState(currentState);
    }

    private static OrderState getCurrentState(Long senderId) {
        return getChatInfo(senderId).getCurrentState();
    }

    private static ChatInfo getChatInfo(Long senderId) {
        return memberOrderInfo.get(senderId);
    }
}
