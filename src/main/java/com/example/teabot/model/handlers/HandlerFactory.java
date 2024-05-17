package com.example.teabot.model.handlers;

import com.example.teabot.model.enums.OrderState;

import java.util.EnumMap;

public class HandlerFactory {
    private static final EnumMap<OrderState, AttributeHandler> stateHandlerMap = initializeHandlers();

    private static EnumMap<OrderState, AttributeHandler> initializeHandlers() {
        final EnumMap<OrderState, AttributeHandler> handlers = new EnumMap<>(OrderState.class);
        handlers.put(OrderState.START, new StartHandler());

        handlers.put(OrderState.TEA_MAKER_BUILDING_PROPOSAL, new TeaMakerProposalHandler());

        handlers.put(OrderState.TEA_BUILDING_TYPE_PROPOSAL, new TeaBuildingProposalHandler());
        handlers.put(OrderState.INPUT_NAME_AWAITING, new TeaNameHandler());
        handlers.put(OrderState.TYPE_SELECTION_AWAITING, new TeaTypeHandler());
        handlers.put(OrderState.COLOR_SELECTION_AWAITING, new ColorHandler());
        handlers.put(OrderState.ADDITIONS_AWAITING, new AdditiveHandler());

        handlers.put(OrderState.CUP_BUILDING_TYPE_PROPOSAL, new CupBuildingTypeHandler());
        handlers.put(OrderState.CUP_NAME_AWAITING, new CupNameHandler());
        handlers.put(OrderState.CUP_SIZE_AWAITING, new CupSizeHandler());

        handlers.put(OrderState.DELICACY_TYPE_AWAITING, new DelicacyTypeHandler());
        handlers.put(OrderState.DELICACY_COUNT_AWAITING, new DelicacyCountHandler());

        handlers.put(OrderState.SAVE_ORDER_AWAITING, new OrderSavingHandler());
        handlers.put(OrderState.CANCEL_ORDER, new CancelHandler());
        handlers.put(OrderState.WITHOUT_ORDER, new WithoutOrderHandler());

        return handlers;
    }

    public static AttributeHandler getHandlerByState(OrderState state) {
        return stateHandlerMap.get(state);
    }
}
