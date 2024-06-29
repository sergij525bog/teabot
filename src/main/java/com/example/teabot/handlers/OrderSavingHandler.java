package com.example.teabot.handlers;

import com.example.teabot.model.orderInfo.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
class OrderSavingHandler implements FinalStateHandler {
    private OrderInfo orderInfo;

    @Override
    public String question() {
        return "Order saved:\n" + orderInfo.toString() + "\nPlease print '/start' to create new order";
    }
}