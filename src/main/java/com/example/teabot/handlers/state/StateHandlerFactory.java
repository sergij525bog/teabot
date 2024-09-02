package com.example.teabot.handlers.state;

import com.example.teabot.handlers.OrderStateHandler;
import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderAttribute;
import com.example.teabot.model.enums.OrderState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.EnumMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StateHandlerFactory {

    private static final Map<OrderState, OrderStateHandler<? extends OrderAttribute>>
            HANDLERS = initializeMap();
    private static final OrderStateHandler<NavigationButtons>
            NAVIGATION_HANDLER = new NavigationHandler();

    private static Map<OrderState, OrderStateHandler<? extends OrderAttribute>> initializeMap() {
        final Map<OrderState, OrderStateHandler<? extends OrderAttribute>> map = new EnumMap<>(OrderState.class);

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
        if (!HANDLERS.containsKey(state)) {
            throw new NullPointerException("There is no handler for state " + state);
        }

        return (OrderStateHandler<T>) HANDLERS.get(state);
    }

    public static OrderStateHandler<NavigationButtons> getNavigationHandler() {
        return NAVIGATION_HANDLER;
    }
}
