package com.example.teabot;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.UpdateParser;
import com.example.teabot.model.enums.AttributeUpdateStatus;
import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.CupBuildingType;
import com.example.teabot.model.enums.delicacy.DelicacyType;
import com.example.teabot.model.enums.tea.Additive;
import com.example.teabot.model.enums.tea.TeaBuildingType;
import com.example.teabot.model.enums.teamaker.Answers;
import com.example.teabot.model.enums.teamaker.MakerSelectingProposals;
import com.example.teabot.model.handlers.AttributeHandler;
import com.example.teabot.model.handlers.HandlerFactory;
import com.example.teabot.model.handlers.OrderSavingHandler;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessages;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.example.teabot.model.enums.OrderState.*;

@Component
public class TeaOrderHandler {
    private final TeaBot bot;

    public TeaOrderHandler(TeaBot bot) {
        this.bot = bot;
        initializeActions();
    }

    private static final Map<Long, ChatInfo> memberOrderInfo = new HashMap<>();
    private static final Map<Long, Long> memberChat = new HashMap<>();
    private static final Map<Long, Long> chatTeaMaker = new HashMap<>();
    private final Map<OrderState, Consumer<Long>> chatOnlyActions = new HashMap<>();
    private final Map<OrderState, BiConsumer<Long, String>> dataActions = new HashMap<>();
    private static final Map<NavigationButtons, BiConsumer<Long, OrderState>> navigationActions = getNavigationActions();

    @SneakyThrows
    public void handle(Update update) {
        UpdateParser parser = UpdateParser.fromUpdate(update);
        processUpdate(parser);
    }

    private void processUpdate(UpdateParser parser) {
        final Long senderId = parser.getSenderId();

        if (memberOrderInfo.get(senderId) != null || isStart(parser.getData())) {
            memberChat.put(senderId, parser.getChatId());
            saveChatMessage(senderId, parser.getMessageId());
            processData(senderId, parser.getData());
        }
    }

    private void processData(Long senderId, String data) {
        if (isStart(data)) {
            setCurrentState(senderId, START);
        }

        if (getCurrentState(senderId) == null) {
            renderError(senderId);
            return;
        }

        if (NavigationButtons.isNavigation(data)) {
            processNavigation(senderId, data);
        }

        processState(senderId, data);
    }

    private void processState(Long senderId, String data) {
        OrderState currentState = getCurrentState(senderId);
        BiConsumer<Long, String> action = dataActions.get(currentState);

        if (action == null) {
            chatOnlyActions.get(currentState).accept(senderId);
        } else {
            action.accept(senderId, data);
        }
    }

    private void cancelOrder(Long senderId) {
        renderMessage(senderId);
        clearChat(senderId);
    }

    private void clearChat(Long senderId) {
        deleteKeyboard(senderId);
        deleteOrderBuildingMessages(senderId);
        memberChat.remove(senderId);
    }

    private void processNavigation(Long senderId, String data) {
        List<OrderState> nextPrevStates = getNextPrevStates(senderId);
        NavigationButtons navigation = NavigationButtons.getInstance(data);

        OrderState state = switch (navigation) {
            case NEXT -> nextPrevStates.get(0);
            case BACK -> nextPrevStates.get(1);
            case CANCEL -> CANCEL_ORDER;
            case SKIP -> SKIP_AND_ORDER;
        };

        navigationActions.get(navigation).accept(senderId, state);
    }

    private void setDefaultsAndOrder(Long senderId) {
        memberOrderInfo.get(senderId).setDefaults();
        saveOrder(senderId);
    }

    private static boolean isStart(String data) {
        return "/start".equals(data) || "/start@YouAreTheTea_bot".equals(data);
    }

    // todo: check data contains only digits
    private void saveDelicacyCount(Long senderId, String data) {
        AttributeHandler handler = getHandler(senderId);
        handler.updateAttribute(data, memberOrderInfo.get(senderId));

        saveOrder(senderId);
    }

    private void saveDelicacyType(Long senderId, String data) {
        if (DelicacyType.NONE.getType().equals(data)) {
            saveOrder(senderId);
        } else {
            processUpdateStatus(senderId, data, DELICACY_COUNT_AWAITING);
        }
    }

    private void proposeSelectCupBuildingType(Long chatId) {
        renderMessage(chatId);
        setCurrentState(chatId, CUP_BUILDING_TYPE_AWAITING);
    }

    private void saveCupName(Long chatId, String data) {
        processUpdateStatus(chatId, data, DELICACY_TYPE_AWAITING);
        if (!NavigationButtons.isNavigation(data)) {
            setPrevState(chatId, CUP_NAME_AWAITING);
        }
    }

    private void saveCupSize(Long chatId, String data) {
        processUpdateStatus(chatId, data, DELICACY_TYPE_AWAITING);
        if (!NavigationButtons.isNavigation(data)) {
            setPrevState(chatId, CUP_SIZE_AWAITING);
        }
    }

    private static void saveChatMessage(Long chatId, Integer messageId) {
        List<Integer> messageIds = memberOrderInfo
                .computeIfAbsent(chatId, k -> new ChatInfo())
                .getMessageIds();
        messageIds.add(messageId);
    }

    private void saveOrder(Long chatId) {
        setCurrentState(chatId, SAVE_ORDER);
        AttributeHandler handler = getHandler(chatId);
        ((OrderSavingHandler) handler).setOrderInfo(memberOrderInfo.get(chatId));

        clearChat(chatId);
    }

    private void saveTeaColor(Long chatId, String data) {
        processUpdateStatus(chatId, data, ADDITIONS_AWAITING);
        if (!NavigationButtons.isNavigation(data)) {
            setPrevState(chatId, COLOR_SELECTION_AWAITING);
        }
    }

    private void processUpdateStatus(Long chatId, String data, OrderState newState) {
        if (NavigationButtons.isNavigation(data)) {
            renderMessage(chatId);
            return;
        }

        AttributeHandler handler = getHandler(chatId);
        AttributeUpdateStatus status = handler.updateAttribute(data, memberOrderInfo.get(chatId));

        if (status == AttributeUpdateStatus.OK) {
            setCurrentState(chatId, newState);
            renderMessage(chatId);
        } else {
            renderError(chatId);
        }
    }

    private void saveAdditive(Long chatId, String data) {
        if(Additive.NONE.getAdditive().equals(data)) {
            setCurrentState(chatId, CUP_BUILDING_TYPE_PROPOSAL);
            proposeSelectCupBuildingType(chatId);
            return;
        }

        OrderState currentState = getCurrentState(chatId);
        processUpdateStatus(chatId, data, currentState);
    }

    private static OrderState getCurrentState(Long chatId) {
        return memberOrderInfo.get(chatId).getCurrentState();
    }

    private void saveTeaType(Long chatId, String data) {
        processUpdateStatus(chatId, data, COLOR_SELECTION_AWAITING);
    }

    private void saveName(Long chatId, String data) {
        processUpdateStatus(chatId, data, ADDITIONS_AWAITING);
        if (!NavigationButtons.isNavigation(data)) {
            setPrevState(chatId, INPUT_NAME_AWAITING);
        }
    }

    private void selectTeaCreationType(Long senderId, String data) {
        Arrays.stream(TeaBuildingType.values())
                .filter(type -> type.getBuildingType().equals(data))
                .findFirst()
                .ifPresentOrElse(type -> {
                    OrderState state = type == TeaBuildingType.BY_NAME ?
                            INPUT_NAME_AWAITING :
                            TYPE_SELECTION_AWAITING;

                    storeUserChoiceAndRender(senderId, state);
                }, () -> renderError(senderId));
    }

    private void selectCupBuildingType(Long senderId, String data) {
        Arrays.stream(CupBuildingType.values())
                .filter(type -> type.getType().equals(data))
                .findFirst()
                .ifPresentOrElse(type -> {
                    OrderState state = type == CupBuildingType.BY_NAME ?
                            CUP_NAME_AWAITING :
                            CUP_SIZE_AWAITING;

                    storeUserChoiceAndRender(senderId, state);
                }, () -> renderError(senderId));
    }

    private void storeUserChoiceAndRender(Long senderId, OrderState state) {
        setNextState(senderId, state);
        setCurrentState(senderId, state);
        renderMessage(senderId);
    }

    private void proposeSelectTeaCreationType(Long chatId) {
        renderMessage(chatId);
        setCurrentState(chatId, TEA_BUILDING_TYPE_AWAITING);
    }

    private void proposeSelectTeaMaker(Long senderId) {
        renderMessage(senderId);
        setCurrentState(senderId, TEA_MAKER_BUILDING_PROPOSAL);
    }

    private void saveTeaMaker(Long senderId, String data) {
        if (Answers.I_CAN_CREATE_TEA.getAnswer().equals(data)) {
            chatTeaMaker.put(getChatId(senderId), senderId);
        }
        processUpdateStatus(senderId, data, TEA_BUILDING_TYPE_AWAITING);
    }


    private void selectTeaMaker(Long senderId, String data) {
        OrderState newState;
        if (MakerSelectingProposals.I_WILL_MAKE_TEA.getMessage().equals(data)){
            newState = TEA_BUILDING_TYPE_AWAITING;
            chatTeaMaker.put(getChatId(senderId), senderId);
        } else {
            newState = TEA_MAKER_SELECTING_AWAITING;
        }

        processUpdateStatus(senderId, data, newState);
    }

    @SneakyThrows
    private void renderError(Long chatId) {
        final AttributeHandler handler = getHandler(chatId);

        final ReplyKeyboard markup = handler != null ? handler.getMarkup() : null;
        renderMessage(chatId, "Unexpected input. Please try again", markup);
    }

    @SneakyThrows
    private void renderMessage(Long chatId) {
        final AttributeHandler handler = getHandler(chatId);

        renderMessage(chatId, handler.question(), handler.getMarkup());
    }

    @SneakyThrows
    private void renderMessage(Long chatId, String text, ReplyKeyboard markup) {
        final List<Integer> messageIds = memberOrderInfo.get(chatId).getMessageIds();

        SendMessage message = buildMessage(chatId, text, markup);
        message.setReplyToMessageId(messageIds.get(messageIds.size() - 1));

        final Integer messageId = bot.execute(message).getMessageId();
        saveChatMessage(chatId, messageId);
    }

    private SendMessage buildMessage(Long senderId, String text, ReplyKeyboard markup) {
        return SendMessage.builder()
                .chatId(getChatId(senderId))
                .text(text)
                .replyMarkup(markup)
                .build();
    }

    @SneakyThrows
    private void deleteKeyboard(Long chatId) {
        final AttributeHandler handler = getHandler(chatId);
        final ReplyKeyboardRemove keyboard = ReplyKeyboardRemove.builder()
                .removeKeyboard(true)
                .selective(true)
                .build();

        final SendMessage message = buildMessage(chatId, handler.question(), keyboard);
        bot.execute(message);
    }

    @SneakyThrows
    private void deleteOrderBuildingMessages(Long chatId) {
        final ChatInfo chatInfo = memberOrderInfo.get(chatId);
        if (chatInfo != null && !chatInfo.getMessageIds().isEmpty()) {
            DeleteMessages deleteMessage = DeleteMessages.builder()
                    .chatId(getChatId(chatId))
                    .messageIds(chatInfo.getMessageIds())
                    .build();

            bot.execute(deleteMessage);
            memberOrderInfo.remove(chatId);
        }
    }

    private static AttributeHandler getHandler(Long chatId) {
        final OrderState currentState = getCurrentState(chatId);
        return HandlerFactory.getHandler(currentState);
    }

    private static void setCurrentState(Long chatId, OrderState newState) {
        final ChatInfo chatInfo = memberOrderInfo.get(chatId);
        chatInfo.setCurrentState(newState);
    }

    private static void setNextState(Long chatId, OrderState next) {
        if (NULL != next) {
            List<OrderState> orderStates = getNextPrevStates(chatId);
            orderStates.set(0, next);
        }
    }

    private static void setPrevState(Long chatId, OrderState prev) {
        if (NULL != prev) {
            List<OrderState> orderStates = getNextPrevStates(chatId);
            orderStates.set(1, prev);
        }
    }

    private static List<OrderState> getNextPrevStates(Long chatId) {
        OrderState currentState = getCurrentState(chatId);
        return memberOrderInfo.get(chatId).getOrderStates().get(currentState);
    }

    private static Long getChatId(Long senderId) {
        return memberChat.get(senderId);
    }

    private void initializeActions() {
        initializeChatActions();
        initializeDataActions();
    }

    private void initializeChatActions() {
        chatOnlyActions.put(START, this::proposeSelectTeaMaker);
        chatOnlyActions.put(TEA_BUILDING_TYPE_PROPOSAL, this::proposeSelectTeaCreationType);
        chatOnlyActions.put(CUP_BUILDING_TYPE_PROPOSAL, this::proposeSelectCupBuildingType);
        chatOnlyActions.put(CANCEL_ORDER, this::cancelOrder);
        chatOnlyActions.put(SKIP_AND_ORDER, this::setDefaultsAndOrder);
    }

    private void initializeDataActions() {
        dataActions.put(TEA_MAKER_BUILDING_PROPOSAL, this::selectTeaMaker);
        dataActions.put(TEA_MAKER_SELECTING_AWAITING, this::saveTeaMaker);
        dataActions.put(TEA_BUILDING_TYPE_AWAITING, this::selectTeaCreationType);
        dataActions.put(INPUT_NAME_AWAITING, this::saveName);
        dataActions.put(TYPE_SELECTION_AWAITING, this::saveTeaType);
        dataActions.put(COLOR_SELECTION_AWAITING, this::saveTeaColor);
        dataActions.put(ADDITIONS_AWAITING, this::saveAdditive);
        dataActions.put(CUP_BUILDING_TYPE_AWAITING, this::selectCupBuildingType);
        dataActions.put(CUP_SIZE_AWAITING, this::saveCupSize);
        dataActions.put(CUP_NAME_AWAITING, this::saveCupName);
        dataActions.put(DELICACY_TYPE_AWAITING, this::saveDelicacyType);
        dataActions.put(DELICACY_COUNT_AWAITING, this::saveDelicacyCount);
    }

    private static Map<NavigationButtons, BiConsumer<Long, OrderState>> getNavigationActions() {
        final Map<NavigationButtons, BiConsumer<Long, OrderState>> navigationActions = new HashMap<>();
        Arrays.stream(NavigationButtons.values())
                .forEach(button -> navigationActions.put(button, TeaOrderHandler::setCurrentState));

        return navigationActions;
    }
}
