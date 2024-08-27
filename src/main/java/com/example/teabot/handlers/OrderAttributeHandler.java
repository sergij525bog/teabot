package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderAttribute;
import com.example.teabot.model.orderInfo.OrderInfo;

public interface OrderAttributeHandler<T extends OrderAttribute> {
    OrderInfo updateOrder(OrderInfo order, T attribute);
}
