package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.OrderAttribute;
import com.example.teabot.model.enums.OrderState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.EnumMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StateHandlerFactory {

    private static final EnumMap<OrderState, OrderStateHandler<? extends OrderAttribute>> handlers = initializeMap();

    private static EnumMap<OrderState, OrderStateHandler<? extends OrderAttribute>> initializeMap() {
        final EnumMap<OrderState, OrderStateHandler<? extends OrderAttribute>> map = new EnumMap<>(OrderState.class);

        map.put(OrderState.START, new StartStateHandler());

        map.put(OrderState.TEA_MAKER_BUILDING_PROPOSAL, new TeaMakerProposalStateHandler());

        map.put(OrderState.TEA_BUILDING_TYPE_PROPOSAL, new TeaBuildingProposalStateHandler());
        map.put(OrderState.INPUT_NAME_AWAITING, new TeaNameStateHandler());
        map.put(OrderState.TYPE_SELECTION_AWAITING, new TeaTypeStateHandler());
        map.put(OrderState.COLOR_SELECTION_AWAITING, new ColorStateHandler());
        map.put(OrderState.ADDITIONS_AWAITING, new AdditiveStateHandler());

        map.put(OrderState.CUP_BUILDING_TYPE_PROPOSAL, new CupBuildingTypeStateHandler());
        map.put(OrderState.CUP_NAME_AWAITING, new CupNameStateHandler());
        map.put(OrderState.CUP_SIZE_AWAITING, new CupSizeStateHandler());

        map.put(OrderState.DELICACY_TYPE_AWAITING, new DelicacyTypeStateHandler());
        map.put(OrderState.DELICACY_COUNT_AWAITING, new DelicacyCountStateHandler());

        return map;
    }

    @SuppressWarnings("unchecked cast")
    public static <T extends OrderAttribute> OrderStateHandler<T> getHandlerByState(OrderState state) {
        if (!handlers.containsKey(state)) {
            throw new NullPointerException("There is no handler for state " + state);
        }

        return (OrderStateHandler<T>) handlers.get(state);
    }
}
