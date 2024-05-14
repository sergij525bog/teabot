package com.example.teabot.model.handlers;

import com.example.teabot.model.enums.OrderState;

import java.util.EnumMap;

import static com.example.teabot.model.enums.OrderState.*;

public class HandlerFactory {
    private static final EnumMap<OrderState, AttributeHandler> stateHandlerMap = new EnumMap<>(OrderState.class);

    static {
        TeaMakerBuildingHandler value = new TeaMakerBuildingHandler();
        stateHandlerMap.put(START, value);
        stateHandlerMap.put(TEA_MAKER_BUILDING_PROPOSAL, value);
        stateHandlerMap.put(TEA_MAKER_SELECTING_AWAITING, new TeaMakerAnswerHandler());
        stateHandlerMap.put(TEA_BUILDING_TYPE_AWAITING, new InitialHandler());
        stateHandlerMap.put(INPUT_NAME_AWAITING, new TeaNameHandler());
        stateHandlerMap.put(TYPE_SELECTION_AWAITING, new TeaTypeHandler());
        stateHandlerMap.put(COLOR_SELECTION_AWAITING, new ColorHandler());
        stateHandlerMap.put(ADDITIONS_AWAITING, new AdditiveHandler());
        stateHandlerMap.put(CUP_BUILDING_TYPE_PROPOSAL, new CupBuildingTypeHandler());
        stateHandlerMap.put(CUP_SIZE_AWAITING, new CupSizeHandler());
        stateHandlerMap.put(CUP_NAME_AWAITING, new CupNameHandler());
        stateHandlerMap.put(DELICACY_TYPE_AWAITING, new DelicacyTypeHandler());
        stateHandlerMap.put(DELICACY_COUNT_AWAITING, new DelicacyCountHandler());
        stateHandlerMap.put(SAVE_ORDER, new OrderSavingHandler());
        stateHandlerMap.put(CANCEL_ORDER, new CancelHandler());
    }

    public static AttributeHandler getHandler(OrderState state) {
        return stateHandlerMap.get(state);
    }
}
