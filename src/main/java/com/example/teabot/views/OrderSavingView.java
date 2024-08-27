package com.example.teabot.views;

import com.example.teabot.model.orderInfo.OrderInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class OrderSavingView implements FinalStateView {
    private final OrderInfo orderInfo;

    @Override
    public String question() {
        return "Order saved:\n" + orderInfo.toString() + "\nPlease print '/start' to create new order";
    }
}
