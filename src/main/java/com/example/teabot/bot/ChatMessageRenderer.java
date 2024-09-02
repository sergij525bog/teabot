package com.example.teabot.bot;

import com.example.teabot.handlers.StateView;
import com.example.teabot.handlers.UserInputHandler;
import com.example.teabot.model.UpdateParser;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.StringUtil;
import com.example.teabot.views.StateViewFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ChatMessageRenderer {
    private static final Map<Long, OrderInfo> memberOrderInfo = new HashMap<>();

    public static void handle(Update update, TeaBot bot) {
        processUpdate(UpdateParser.fromUpdate(update), bot);
    }

    private static void processUpdate(UpdateParser parser, TeaBot bot) {
        storeOrderAndChatInfoIfNeeded(parser);

        final Long senderId = parser.getSenderId();
        if (memberCreatingOrder(senderId)) {
            final OrderState newState = UserInputHandler
                    .handle(getChatInfo(senderId), parser.getAttributeUpdate())
                    .getCurrentState();

            processNewState(senderId, newState, bot);

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

    private static void processNewState(Long senderId, OrderState newState, TeaBot bot) {
        if (OrderState.isFinal(newState)) {
            ChatHandler.clearChatMessages(senderId, bot);
            ChatHandler.markChatToDelete(senderId);
        }
    }

    private static void renderMessage(Long senderId, TeaBot bot) {
        final var view = getView(senderId, getCurrentState(senderId));

        ChatHandler.renderMessage(senderId, view, bot);
    }

    private static void clearOrderAndChatInfoIfNeeded(Long senderId) {
        if (ChatHandler.chatShouldBeDeleted(senderId)) {
            ChatHandler.clearChatInfo(senderId);

            memberOrderInfo.remove(senderId);
        }
    }

    private static StateView getView(Long senderId, OrderState state) {
        if (state == OrderState.ERROR) {
            return StateViewFactory.getErrorView(getChatInfo(senderId));
        }

        if (state == OrderState.SAVE_ORDER_AWAITING) {
            return StateViewFactory.getOrderSavingView(getChatInfo(senderId));
        }

        return StateViewFactory.getViewByState(state);
    }

    private static boolean memberCreatingOrder(Long senderId) {
        return memberOrderInfo.containsKey(senderId);
    }

    private static OrderState getCurrentState(Long senderId) {
        return getChatInfo(senderId).getCurrentState();
    }

    private static OrderInfo getChatInfo(Long senderId) {
        final OrderInfo info = memberOrderInfo.get(senderId);
        if (info != null) {
            return info;
        }

        throw new NullPointerException("There is no order info for member with id " + senderId);
    }
}
