package com.example.teabot.model;

import com.example.teabot.model.enums.OrderState;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.teabot.model.enums.OrderState.*;

@Getter
@Setter
public class ChatInfo {
    private final Tea tea = new Tea();
    private final Cup cup = new Cup();
    private final Delicacy delicacy = new Delicacy();
    private boolean isTeaMaker = false;

    private OrderState currentState;
    private final EnumMap<OrderState, List<OrderState>> orderStates = new EnumMap<>(OrderState.class);
    private final List<Integer> messageIds = new LinkedList<>();

    public ChatInfo() {
        fillAlreadyKnownStates();
    }

    private void fillAlreadyKnownStates() {
        orderStates.put(NULL, asList(NULL, NULL));
        orderStates.put(START, asList(TEA_MAKER_SELECTING_AWAITING, NULL));
        orderStates.put(TEA_MAKER_SELECTING_AWAITING, asList(TEA_BUILDING_TYPE_AWAITING, START));
        orderStates.put(TEA_BUILDING_TYPE_AWAITING, asList(NULL, START));
        orderStates.put(INPUT_NAME_AWAITING, asList(ADDITIONS_AWAITING, START));
        orderStates.put(TYPE_SELECTION_AWAITING, asList(COLOR_SELECTION_AWAITING, START));
        orderStates.put(COLOR_SELECTION_AWAITING, asList(ADDITIONS_AWAITING, TYPE_SELECTION_AWAITING));
        orderStates.put(ADDITIONS_AWAITING, asList(CUP_BUILDING_TYPE_PROPOSAL, NULL));
        orderStates.put(CUP_BUILDING_TYPE_PROPOSAL, asList(CUP_BUILDING_TYPE_AWAITING, ADDITIONS_AWAITING));
        orderStates.put(CUP_BUILDING_TYPE_AWAITING, asList(NULL, ADDITIONS_AWAITING));
        orderStates.put(CUP_SIZE_AWAITING, asList(DELICACY_TYPE_AWAITING, CUP_BUILDING_TYPE_PROPOSAL));
        orderStates.put(CUP_NAME_AWAITING, asList(DELICACY_TYPE_AWAITING, CUP_BUILDING_TYPE_PROPOSAL));
        orderStates.put(DELICACY_TYPE_AWAITING, asList(DELICACY_COUNT_AWAITING, NULL));
        orderStates.put(DELICACY_COUNT_AWAITING, asList(SAVE_ORDER, DELICACY_TYPE_AWAITING));
    }

    private static List<OrderState> asList(OrderState next, OrderState prev) {
        return Stream.of(next, prev).collect(Collectors.toList());
    }

    public void setDefaults() {
        tea.setDefaults();
        cup.setDefaults();
        delicacy.setDefaults();
    }

    @Override
    public String toString() {
        return "tea: " + tea +
                "\ncup: " + cup +
                "\ndelicacy: " + delicacy +
                "\nis tea maker: " + isTeaMaker;
    }
}
