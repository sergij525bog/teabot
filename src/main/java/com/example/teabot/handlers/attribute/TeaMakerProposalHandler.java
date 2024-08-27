package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.teamaker.MakerSelectingProposals;
import com.example.teabot.model.orderInfo.OrderInfo;

class TeaMakerProposalHandler implements OrderAttributeHandler<MakerSelectingProposals> {

    @Override
    public OrderInfo updateOrder(OrderInfo order, MakerSelectingProposals attribute) {
        final boolean setTeaMaker = attribute != MakerSelectingProposals.I_WANT_TEA;
        order.setTeaMaker(setTeaMaker);

        return order;
    }
}
