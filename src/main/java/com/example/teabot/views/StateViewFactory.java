package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.orderInfo.OrderInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.EnumMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StateViewFactory {

    private static final Map<OrderState, StateView> views = initializeMap();

    private static Map<OrderState, StateView> initializeMap() {
        final Map<OrderState, StateView> map = new EnumMap<>(OrderState.class);

        map.put(OrderState.START, new StartView());

        map.put(OrderState.TEA_MAKER_BUILDING_PROPOSAL, new TeaMakerProposalView());

        map.put(OrderState.TEA_BUILDING_TYPE_PROPOSAL, new TeaBuildingProposalView());
        map.put(OrderState.INPUT_NAME_AWAITING, new TeaNameView());
        map.put(OrderState.TYPE_SELECTION_AWAITING, new TeaTypeView());
        map.put(OrderState.COLOR_SELECTION_AWAITING, new ColorStateView());
        map.put(OrderState.ADDITIONS_AWAITING, new AdditiveStateView());

        map.put(OrderState.CUP_BUILDING_TYPE_PROPOSAL, new CupBuildingTypeView());
        map.put(OrderState.CUP_NAME_AWAITING, new CupNameView());
        map.put(OrderState.CUP_SIZE_AWAITING, new CupSizeView());

        map.put(OrderState.DELICACY_TYPE_AWAITING, new DelicacyTypeView());
        map.put(OrderState.DELICACY_COUNT_AWAITING, new DelicacyCountView());

        map.put(OrderState.CANCEL_ORDER, new CancelStateView());
        map.put(OrderState.WITHOUT_ORDER, new WithoutOrderView());

        return map;
    }

    public static StateView getViewByState(final OrderState state) {
        if (!views.containsKey(state)) {
            throw new NullPointerException("There is no view for state " + state);
        }

        return views.get(state);
    }

    public static StateView getErrorView(OrderInfo order) {
        final OrderState prevState = order.getPrevState();
        final var view = getViewByState(prevState);

        return new ErrorView(view);
    }

    public static StateView getOrderSavingView(OrderInfo order) {
        return new OrderSavingView(order);
    }
}
