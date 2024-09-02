package com.example.teabot.utils;

import com.example.teabot.model.enums.OrderAttribute;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.orderInfo.OrderInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {
    private final static Random RANDOM = new Random();

    public static <T extends OrderAttribute> T getRandomAttribute(T[] attributes) {
        return attributes[RANDOM.nextInt(attributes.length)];
    }

    public static OrderInfo getOrder(OrderState initialState) {
        final OrderInfo order = new OrderInfo();
        order.setCurrentState(initialState);

        return order;
    }
}
