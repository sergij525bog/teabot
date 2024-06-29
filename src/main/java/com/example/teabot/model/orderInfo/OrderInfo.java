package com.example.teabot.model.orderInfo;

import com.example.teabot.model.enums.OrderState;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.teabot.model.enums.OrderState.*;

@Getter
@Setter
public class OrderInfo {
    private final Tea tea = new Tea();
    private final Cup cup = new Cup();
    private final Delicacy delicacy = new Delicacy();
    private boolean isTeaMaker = false;

    private OrderState currentState = START;
    private final EnumMap<OrderState, List<OrderState>> orderStates = new EnumMap<>(OrderState.class);

    public OrderInfo() {
        fillAlreadyKnownStates();
    }

    private void fillAlreadyKnownStates() {
        orderStates.put(START, asList(TEA_MAKER_BUILDING_PROPOSAL, START));
        orderStates.put(TEA_MAKER_BUILDING_PROPOSAL, asList(TEA_MAKER_BUILDING_PROPOSAL, START));
        orderStates.put(TEA_BUILDING_TYPE_PROPOSAL, asList(TEA_BUILDING_TYPE_PROPOSAL, NULL));
        orderStates.put(INPUT_NAME_AWAITING, asList(ADDITIONS_AWAITING, TEA_BUILDING_TYPE_PROPOSAL));
        orderStates.put(TYPE_SELECTION_AWAITING, asList(COLOR_SELECTION_AWAITING, TEA_BUILDING_TYPE_PROPOSAL));
        orderStates.put(COLOR_SELECTION_AWAITING, asList(ADDITIONS_AWAITING, TYPE_SELECTION_AWAITING));
        orderStates.put(ADDITIONS_AWAITING, asList(CUP_BUILDING_TYPE_PROPOSAL, NULL));
        orderStates.put(CUP_BUILDING_TYPE_PROPOSAL, asList(CUP_BUILDING_TYPE_PROPOSAL, ADDITIONS_AWAITING));
        orderStates.put(CUP_SIZE_AWAITING, asList(DELICACY_TYPE_AWAITING, CUP_BUILDING_TYPE_PROPOSAL));
        orderStates.put(CUP_NAME_AWAITING, asList(DELICACY_TYPE_AWAITING, CUP_BUILDING_TYPE_PROPOSAL));
        orderStates.put(DELICACY_TYPE_AWAITING, asList(DELICACY_COUNT_AWAITING, NULL));
        orderStates.put(DELICACY_COUNT_AWAITING, asList(SAVE_ORDER_AWAITING, DELICACY_TYPE_AWAITING));
        orderStates.put(WITHOUT_ORDER, asList(WITHOUT_ORDER, NULL));
    }

    private static List<OrderState> asList(OrderState next, OrderState prev) {
        return Stream.of(next, prev).collect(Collectors.toList());
    }

    public OrderState getNextState() {
        return getNextState(currentState);
    }

    public OrderState getNextState(OrderState currentState) {
        return getOrderStates(currentState).get(0);
    }

    public void setNextState(OrderState nextState) {
        setNextState(currentState, nextState);
    }

    public void setNextState(OrderState currentState, OrderState nextState) {
        getOrderStates(currentState).set(0, nextState);
    }

    public OrderState getPrevState() {
        return getPrevState(currentState);
    }

    public OrderState getPrevState(OrderState currentState) {
        return getOrderStates(currentState).get(1);
    }

    public void setPrevState(OrderState prevState) {
        setPrevState(currentState, prevState);
    }

    public void setPrevState(OrderState currentState, OrderState prevState) {
        getOrderStates(currentState).set(1, prevState);
    }

    private List<OrderState> getOrderStates(OrderState currentState) {
        return orderStates.get(currentState);
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
                "\ndelicacy: " + delicacy;
    }
}
