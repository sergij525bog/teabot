package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.OrderAttribute;
import com.example.teabot.model.enums.OrderState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.EnumMap;
import java.util.Map;

@SuppressWarnings("unchecked cast")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AttributeHandlerFactory {
    private static final Map<OrderState, OrderAttributeHandler<? extends OrderAttribute>>
            HANDLERS = initializeHandlers();

    private static Map<OrderState, OrderAttributeHandler<? extends OrderAttribute>> initializeHandlers() {
        final Map<OrderState, OrderAttributeHandler<? extends OrderAttribute>> handlers =
                new EnumMap<>(OrderState.class);

        handlers.put(OrderState.TEA_MAKER_BUILDING_PROPOSAL, new TeaMakerProposalHandler());

        handlers.put(OrderState.INPUT_NAME_AWAITING, new TeaNameHandler());
        handlers.put(OrderState.TYPE_SELECTION_AWAITING, new TeaTypeHandler());
        handlers.put(OrderState.COLOR_SELECTION_AWAITING, new ColorHandler());
        handlers.put(OrderState.ADDITIONS_AWAITING, new AdditiveHandler());

        handlers.put(OrderState.CUP_NAME_AWAITING, new CupNameHandler());
        handlers.put(OrderState.CUP_SIZE_AWAITING, new CupSizeHandler());

        handlers.put(OrderState.DELICACY_TYPE_AWAITING, new DelicacyTypeHandler());
        handlers.put(OrderState.DELICACY_COUNT_AWAITING, new DelicacyCountHandler());

        return handlers;
    }

    public static <T extends OrderAttribute> OrderAttributeHandler<T> getHandlerByState(OrderState state) {
        if (!HANDLERS.containsKey(state)) {
            throw new NullPointerException("There is no handler for state " + state);
        }

        return (OrderAttributeHandler<T>) HANDLERS.get(state);
    }
}
