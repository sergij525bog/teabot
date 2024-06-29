package com.example.teabot.handlers;

import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.model.enums.OrderState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.EnumMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HandlerFactory {
    private static final EnumMap<OrderState, OrderAttributeHandler> stateHandlerMap = initializeHandlers();

    private static EnumMap<OrderState, OrderAttributeHandler> initializeHandlers() {
        final EnumMap<OrderState, OrderAttributeHandler> handlers = new EnumMap<>(OrderState.class);
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

    public static OrderAttributeHandler getHandlerByState(OrderState state) {
        return stateHandlerMap.get(state);
    }

    public static OrderAttributeHandler getErrorHandler(OrderAttributeHandler lastWorkerHandler) {
        return new ErrorHandler(lastWorkerHandler);
    }

    public static OrderAttributeHandler getNavigationHandler() {
        return new NavigationHandler();
    }

    public static OrderAttributeHandler getOrderSavingHandler(OrderInfo orderInfo) {
        return new OrderSavingHandler(orderInfo);
    }
}
