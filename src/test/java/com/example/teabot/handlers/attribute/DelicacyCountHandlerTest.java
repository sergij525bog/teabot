package com.example.teabot.handlers.attribute;

import com.example.teabot.handlers.OrderAttributeHandler;
import com.example.teabot.model.enums.delicacy.DelicacyCount;
import com.example.teabot.model.orderInfo.OrderInfo;
import com.example.teabot.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DelicacyCountHandlerTest {
    private final OrderAttributeHandler<DelicacyCount> handler = new DelicacyCountHandler();

    @Test
    void itShouldThrowIfCountEqualToZero() {
        assertThrows(
                IllegalArgumentException.class,
                () -> handler.updateOrder(new OrderInfo(), DelicacyCount.ZERO));
    }

    @Test
    void itShouldSaveCount() {
        final DelicacyCount count = TestUtils.getRandomAttribute(naturalValues());

        final OrderInfo order = handler.updateOrder(new OrderInfo(), count);

        assertEquals(count, order.getDelicacy().getCount());
    }

    private static DelicacyCount[] naturalValues() {
        return Stream.of(DelicacyCount.values())
                .filter(v -> v != DelicacyCount.ZERO)
                .toArray(DelicacyCount[]::new);
    }
}