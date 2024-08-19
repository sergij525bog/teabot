package com.example.teabot.bot;

import com.example.teabot.handlers.HandlerFactory;
import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.ChatHandler;
import com.example.teabot.model.UpdateParser;
import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.StringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ChatMessageRenderer {
    private static final Map<Long, OrderInfo> memberOrderInfo = new HashMap<>();
    private static final Map<Long, OrderAttributeHandler> handlersWithErrorState = new HashMap<>();

    public static void handle(Update update, TeaBot bot) {
        processUpdate(UpdateParser.fromUpdate(update), bot);
    }

    private static void processUpdate(UpdateParser parser, TeaBot bot) {
        storeOrderAndChatInfoIfNeeded(parser);

        final Long senderId = parser.getSenderId();
        if (memberCreatingOrder(senderId)) {
            final OrderState stateBeforeHandling = getCurrentState(senderId);
            final OrderState newState = updateAttribute(senderId, parser.getAttributeUpdate());

            processNewState(senderId, stateBeforeHandling, newState, bot);

            renderMessage(senderId, bot);
            clearOrderAndChatInfoIfNeeded(senderId);
        }
    }

    private static void storeOrderAndChatInfoIfNeeded(UpdateParser parser) {
        final Long senderId = parser.getSenderId();
        final OrderInfo orderInfo = memberOrderInfo.get(senderId);

        if (orderInfo == null && StringUtil.isStartCommand(parser.getAttributeUpdate())) {
            memberOrderInfo.put(senderId, new OrderInfo());
        }

        if (memberCreatingOrder(senderId)) {
            ChatHandler.storeChatInfo(parser);
        }
    }

    private static OrderState updateAttribute(Long senderId, String data) {
        return getHandlerForCurrentState(senderId, data)
                .updateOrder(getChatInfo(senderId), data)
                .getCurrentState();
    }

    private static void processNewState(Long senderId, OrderState lastWorkingState, OrderState newState, TeaBot bot) {
        if (newState == OrderState.ERROR) {
            saveLastActiveHandler(senderId, lastWorkingState);
            return;
        }

        if (OrderState.isFinalState(newState)) {
            ChatHandler.clearChatMessages(senderId, bot);
            ChatHandler.markChatToDelete(senderId);
        }
    }

    private static void renderMessage(Long senderId, TeaBot bot) {
        final var handler = getHandlerForCurrentState(senderId);

        ChatHandler.renderMessage(
                senderId,
                handler.question(),
                handler.getMarkup(),
                bot
        );
    }

    private static void clearOrderAndChatInfoIfNeeded(Long senderId) {
        if (ChatHandler.chatShouldBeDeleted(senderId)) {
            ChatHandler.clearChatInfo(senderId);

            memberOrderInfo.remove(senderId);
            handlersWithErrorState.remove(senderId);
        }
    }

    private static void saveLastActiveHandler(Long senderId, OrderState lastWorkingState) {
        handlersWithErrorState.put(
                senderId,
                HandlerFactory.getHandlerByState(lastWorkingState)
        );
    }

    private static OrderAttributeHandler getHandlerForCurrentState(Long senderId, String data) {
        if (NavigationButtons.isNavigation(data)) {
            return HandlerFactory.getNavigationHandler();
        }

        return getHandlerForCurrentState(senderId);
    }

    private static OrderAttributeHandler getHandlerForCurrentState(Long senderId) {
        return getHandlerForState(senderId, getCurrentState(senderId));
    }

    private static OrderAttributeHandler getHandlerForState(Long senderId, OrderState state) {
        if (state == OrderState.ERROR) {
            return HandlerFactory.getErrorHandler(handlersWithErrorState.get(senderId));
        }

        if (state == OrderState.SAVE_ORDER_AWAITING) {
            return HandlerFactory.getOrderSavingHandler(getChatInfo(senderId));
        }

        return HandlerFactory.getHandlerByState(state);
    }

    private static boolean memberCreatingOrder(Long senderId) {
        return memberOrderInfo.containsKey(senderId);
    }

    private static OrderState getCurrentState(Long senderId) {
        return getChatInfo(senderId).getCurrentState();
    }

    private static OrderInfo getChatInfo(Long senderId) {
        OrderInfo info = memberOrderInfo.get(senderId);
        if (info != null) {
            return info;
        }

        throw new NullPointerException("There is no order info for member with id " + senderId);
    }
}
