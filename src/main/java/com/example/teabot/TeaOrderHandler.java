package com.example.teabot;

import com.example.teabot.model.*;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.cup.CupBuildingType;
import com.example.teabot.model.enums.delicacy.DelicacyType;
import com.example.teabot.model.enums.tea.Additive;
import com.example.teabot.model.enums.tea.Color;
import com.example.teabot.model.enums.tea.TeaBuildingType;
import com.example.teabot.model.enums.tea.Type;
import com.example.teabot.model.views.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessages;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.*;

@RequiredArgsConstructor
public class TeaOrderHandler {
    public static final String INCORRECT_INPUT_MESSAGE = "Incorrect input. Please input '/start' command";
    private final TeaBot bot;
    private TeaOrder order;
//    private Long teaMakerChatId = 348211570L;

    private static final Map<Long, ChatInfo> chatInfoMap = new HashMap<>();
    private static final Map<Long, OrderState> orderStates = new HashMap<>();

    @SneakyThrows
    public void handle(Update update) {
        UpdateParser parser = UpdateParser.fromUpdate(update);
        processUpdate(parser);
    }

    private void processUpdate(UpdateParser parser) {
        final Long chatId = parser.getChatId();
        final String data = parser.getData();

        initializeChatInfo(chatId);
        saveChatMessage(chatId, parser.getMessageId());

        if ("/start".equals(data)) {
            orderStates.put(chatId, OrderState.START);
        }
        if ("next".equals(data)) {
            orderStates.put(chatId, OrderState.CUP_BUILDING_TYPE_PROPOSAL);
        }

        if (orderStates.get(chatId) == null) {
            renderError(chatId, data);
            return;
        }

        switch (orderStates.get(chatId)) {
            case START -> proposeSelectTeaCreationType(chatId);
            case TEA_BUILDING_TYPE_AWAITING -> selectTeaCreationType(chatId, data);
            case INPUT_NAME_AWAITING -> saveName(chatId, data);
            case TYPE_SELECTION_AWAITING -> saveTeaType(chatId, data);
            case COLOR_SELECTION_AWAITING -> saveTeaColor(chatId, data);
            case ADDITIONS_AWAITING -> saveAdditive(chatId, data);
            case CUP_BUILDING_TYPE_PROPOSAL -> proposeSelectCupBuildingType(chatId);
            case CUP_BUILDING_TYPE_AWAITING -> selectCupBuildingType(chatId, data);
            case CUP_SIZE_AWAITING -> saveCupSize(chatId, data);
            case CUP_NAME_AWAITING -> saveCupName(chatId, data);
            case DELICACY_TYPE_AWAITING -> saveDelicacyType(chatId, data);
            case DELICACY_COUNT_AWAITING -> saveDelicacyCount(chatId, data);
//            case SAVE_ORDER_AWAITING -> saveOrder(chatId);
        }
    }

    // todo: check data contains only digits
    private void saveDelicacyCount(Long chatId, String data) {
        Delicacy delicacy = getDelicacy(chatId);
        delicacy.setCount(Byte.parseByte(data));

        saveOrder(chatId);
    }

    private void saveDelicacyType(Long chatId, String data) {
        Arrays.stream(DelicacyType.values())
                .filter(type -> type.getType().equals(data))
                .findFirst()
                .ifPresentOrElse(
                        type -> {
                            Delicacy delicacy = getDelicacy(chatId);
                            delicacy.setType(type);

                            orderStates.put(chatId, OrderState.DELICACY_COUNT_AWAITING);
                            changeView(chatId, new DelicacyCountButtonView(delicacy));
                            renderMessage(chatId);
                }, () -> renderError(chatId, data));
    }

    private void selectCupBuildingType(Long chatId, String data) {
        OrderState state;
        final Cup cup = getCup(chatId);
        if (CupBuildingType.BY_NAME.getType().equals(data)) {
            changeView(chatId, new CupNameButtonView(cup));
            state = OrderState.CUP_NAME_AWAITING;
        } else if (CupBuildingType.BY_SIZE.getType().equals(data)) {
            changeView(chatId, new CupSizeButtonView(cup));
            state = OrderState.CUP_SIZE_AWAITING;
        } else {
            renderError(chatId, data);
            return;
        }

        renderMessage(chatId);
        orderStates.put(chatId, state);
    }

    private void proposeSelectCupBuildingType(Long chatId) {
        changeView(chatId, new CupBuildingTypeButtonView(getCup(chatId)));
        orderStates.put(chatId, OrderState.CUP_BUILDING_TYPE_AWAITING);
        renderMessage(chatId);
    }

    private void saveCupName(Long chatId, String data) {
        Cup cup = getCup(chatId);
        cup.setName(data);

        orderStates.put(chatId, OrderState.DELICACY_TYPE_AWAITING);
        changeView(chatId, new DelicacyTypeButtonView(getDelicacy(chatId)));
        renderMessage(chatId);
    }

    private void saveCupSize(Long chatId, String data) {
        Cup cup = getCup(chatId);
        cup.setSize(data);

        orderStates.put(chatId, OrderState.DELICACY_TYPE_AWAITING);
        changeView(chatId, new DelicacyTypeButtonView(getDelicacy(chatId)));
        renderMessage(chatId);
    }

    private void saveChatMessage(Long chatId, Integer messageId) {
        List<Integer> messageIds = chatInfoMap
                .computeIfAbsent(chatId, k -> new ChatInfo())
                .messageIds;
        messageIds.add(messageId);
    }

    private void saveOrder(Long chatId) {
        changeView(chatId, new StopView(getTea(chatId), chatInfoMap.get(chatId)));

        deleteKeyboard(chatId);
        orderStates.remove(chatId);
        clearUserMessages(chatId);
    }

    @SneakyThrows
    private void clearUserMessages(Long chatId) {
        final ChatInfo chatInfo = chatInfoMap.get(chatId);
        if (chatInfo != null && !chatInfo.messageIds.isEmpty()) {
            DeleteMessages deleteMessage = DeleteMessages.builder()
                    .chatId(chatId)
                    .messageIds(chatInfo.messageIds)
                    .build();

            bot.execute(deleteMessage);
            chatInfoMap.remove(chatId);
        }
    }

    @SneakyThrows
    private void saveTeaColor(Long chatId, String data) {
        Arrays.stream(Color.values())
                .filter(color -> color.getColor().equals(data))
                .findFirst()
                .ifPresentOrElse(color -> {
                    final Tea tea = getTea(chatId);
                    tea.setColor(color);

                    changeView(chatId, new AdditiveButtonView(tea));

                    renderMessage(chatId);
                    orderStates.put(chatId, OrderState.ADDITIONS_AWAITING);
                }, () -> renderError(chatId, data));
    }

    private void saveAdditive(Long chatId, String data) {
        final Additive teaAdditive = Arrays.stream(Additive.values())
                .filter(additive -> additive.getAdditive().equals(data))
                .findFirst()
                .orElseThrow();

        final Tea tea = getTea(chatId);
        tea.getAdditives().add(teaAdditive);
    }

    @SneakyThrows
    private void deleteKeyboard(Long chatId) {
        ReplyKeyboardRemove remove = ReplyKeyboardRemove.builder()
                .removeKeyboard(true)
                .build();
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(getView(chatId).question())
                .replyMarkup(remove)
                .build();
        bot.execute(sendMessage);
    }

    private void saveTeaType(Long chatId, String data) {
        Arrays.stream(Type.values())
                .filter(type -> type.getType().equals(data))
                .findFirst()
                .ifPresentOrElse(type -> {
                    final Tea tea = getTea(chatId);
                    tea.setType(type);

                    changeView(chatId, new ColorButtonView(tea));

                    renderMessage(chatId);
                    orderStates.put(chatId, OrderState.COLOR_SELECTION_AWAITING);
                }, () -> renderError(chatId, data));
    }

    private void saveName(Long chatId, String data) {
        final Tea tea = getTea(chatId);
        tea.setName(data);

        changeView(chatId, new AdditiveButtonView(tea));

        renderMessage(chatId);
        orderStates.put(chatId, OrderState.ADDITIONS_AWAITING);
    }

    private void selectTeaCreationType(Long chatId, String data) {
        OrderState state;
        final Tea tea = getTea(chatId);
        if (TeaBuildingType.BY_NAME.getBuildingType().equals(data)) {
            changeView(chatId, new TeaNameButtonView(tea));
            state = OrderState.INPUT_NAME_AWAITING;
        } else if (TeaBuildingType.BY_DESCRIPTION.getBuildingType().equals(data)) {
            changeView(chatId, new TeaTypeButtonView(tea));
            state = OrderState.TYPE_SELECTION_AWAITING;
        } else {
            renderError(chatId, data);
            return;
        }

        renderMessage(chatId);
        orderStates.put(chatId, state);
    }

    @SneakyThrows
    private void renderError(Long chatId, String data) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Unexpected input: " + data + ". Please try again")
                .build();

        bot.execute(message);
    }

    private void proposeSelectTeaCreationType(Long chatId) {
        orderStates.put(chatId, OrderState.TEA_BUILDING_TYPE_AWAITING);
        renderMessage(chatId);
    }

    @SneakyThrows
    private void renderMessage(Long chatId) {
        final View view = getView(chatId);
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(view.question())
                .replyMarkup(view.getMarkup())
                .build();
        final Integer messageId = bot.execute(sendMessage).getMessageId();
        saveChatMessage(chatId, messageId);
    }

    private void changeView(Long chatId, View view) {
        ChatInfo chatInfo = chatInfoMap.get(chatId);

        chatInfo.view = view;
    }

    private void initializeChatInfo(Long chatId) {
        chatInfoMap.putIfAbsent(chatId, new ChatInfo());
    }

    private static View getView(Long chatId) {
        final ChatInfo chatInfo = chatInfoMap.get(chatId);

        if (chatInfo == null) {
            throw new RuntimeException("Chat info cannot be null");
        }

        return chatInfo.view;
    }

    private static Tea getTea(Long chatId) {
        final ChatInfo chatInfo = chatInfoMap.get(chatId);

        if (chatInfo == null) {
            throw new RuntimeException("Chat info cannot be null");
        }

        return chatInfo.tea;
    }

    private static Cup getCup(Long chatId) {
        final ChatInfo chatInfo = chatInfoMap.get(chatId);

        if (chatInfo == null) {
            throw new RuntimeException("Chat info cannot be null");
        }

        return chatInfo.cup;
    }

    private static Delicacy getDelicacy(Long chatId) {
        final ChatInfo chatInfo = chatInfoMap.get(chatId);

        if (chatInfo == null) {
            throw new RuntimeException("Chat info cannot be null");
        }

        return chatInfo.delicacy;
    }

    @NoArgsConstructor
    public static class ChatInfo {
        private final Tea tea = new Tea();
        private final Cup cup = new Cup();
        private final Delicacy delicacy = new Delicacy();
        private View view = new InitialView(tea);
        private final List<Integer> messageIds = new LinkedList<>();

        @Override
        public String toString() {
            return "tea: " + tea +
                    "\ncup: " + cup +
                    "\ndelicacy: " + delicacy;
        }
    }
}
