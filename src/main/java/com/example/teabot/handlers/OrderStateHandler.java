package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderAttribute;
import com.example.teabot.model.orderInfo.OrderInfo;

public interface OrderStateHandler<T extends OrderAttribute> {
     OrderInfo updateOrderState(OrderInfo order, T param);
}
