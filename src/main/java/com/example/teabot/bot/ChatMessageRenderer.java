package com.example.teabot.bot;

import com.example.teabot.model.ChatHandler;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.UpdateParser;
import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.handlers.HandlerFactory;
import com.example.teabot.model.handlers.OrderAttributeHandler;
import com.example.teabot.utils.StringUtil;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

class ChatMessageRenderer {
    private final TeaBot bot;

    public ChatMessageRenderer(TeaBot bot) {
        this.bot = bot;
    }

    private static final Map<Long, OrderInfo> memberOrderInfo = new HashMap<>();
    private static final Map<Long, OrderAttributeHandler> lastWorkedHandler = new HashMap<>();

    public void handle(Update update) {
        processUpdate(UpdateParser.fromUpdate(update));
    }

    private void processUpdate(UpdateParser parser) {
        storeOrderAndChatInfoIfNeeded(parser);

        final Long senderId = parser.getSenderId();
        if (memberCreatingOrder(senderId)) {
            final OrderState stateBeforeHandling = getCurrentState(senderId);
            final OrderState newState = handleData(senderId, parser.getData());

            processNewState(senderId, stateBeforeHandling, newState);

            renderMessage(senderId);
            clearOrderAndChatInfoIfNeeded(senderId);
        }
    }

    private static void storeOrderAndChatInfoIfNeeded(UpdateParser parser) {
        final Long senderId = parser.getSenderId();
        final OrderInfo orderInfo = memberOrderInfo.get(senderId);

        if (orderInfo == null && StringUtil.isStartCommand(parser.getData())) {
            memberOrderInfo.put(senderId, new OrderInfo());
        }

        if (memberCreatingOrder(senderId)) {
            ChatHandler.storeChatInfo(parser);
        }
    }

    private OrderState handleData(Long senderId, String data) {
        final OrderState currentState = getCurrentState(senderId);
        final OrderAttributeHandler handler = getHandler(senderId, data, currentState);

        if (handler != null) {
            return handler.processUserInput(data, getChatInfo(senderId));
        }

        throw new NullPointerException("There is no actions for state " + currentState);
    }

    private void processNewState(Long senderId, OrderState currentState, OrderState newState) {
//        todo: this case should be impossible. delete it after testing
//        if (newState == OrderState.START) {
//            ChatHandler.markChatToDelete(senderId);
//            return;
//        }

        if (newState == OrderState.ERROR) {
            saveLastWorkedHandler(senderId, currentState);
        }

        setCurrentState(senderId, newState);

        if (OrderState.isFinalState(newState)) {
            ChatHandler.clearChatMessages(senderId, bot);
            ChatHandler.markChatToDelete(senderId);
        }
    }

    private void renderMessage(Long senderId) {
        final OrderState currentState = getCurrentState(senderId);
//        todo: this statement should always be equal to true. delete it after testing
//        if (currentState != OrderState.START) {
        final OrderAttributeHandler handler = getHandler(senderId, currentState);
        ChatHandler.renderMessage(senderId, handler.question(), handler.getMarkup(), bot);
//        }
    }

    private void clearOrderAndChatInfoIfNeeded(Long senderId) {
        if (ChatHandler.chatShouldBeDeleted(senderId)) {
            ChatHandler.clearChatInfo(senderId);

            memberOrderInfo.remove(senderId);
            lastWorkedHandler.remove(senderId);
        }
    }

    private void saveLastWorkedHandler(Long senderId, OrderState currentState) {
        lastWorkedHandler.put(senderId, getHandler(senderId, currentState));
    }

    private OrderAttributeHandler getHandler(Long senderId, String data, OrderState state) {
        if (NavigationButtons.isNavigation(data)) {
            return HandlerFactory.getNavigationHandler();
        }
        return getHandler(senderId, state);
    }

    private OrderAttributeHandler getHandler(Long senderId, OrderState state) {
        if (state == OrderState.ERROR) {
            return HandlerFactory.getErrorHandler(lastWorkedHandler.get(senderId));
        }

        if (state == OrderState.SAVE_ORDER_AWAITING) {
            return HandlerFactory.getOrderSavingHandler(getChatInfo(senderId));
        }

        return HandlerFactory.getHandlerByState(state);
    }

    private static boolean memberCreatingOrder(Long senderId) {
        return memberOrderInfo.containsKey(senderId);
    }

    private static void setCurrentState(Long senderId, OrderState currentState) {
        getChatInfo(senderId).setCurrentState(currentState);
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
