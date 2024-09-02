package com.example.teabot.views;

import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderAttribute;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class KeyboardFactory {
    private static final Map<Class<? extends OrderAttribute>, ReplyKeyboard>
            KEYBOARDS = new HashMap<>();
    private static final KeyboardRow NAVIGATION_ROW = createNavigationRow();
    private static final int GROUP_SIZE = 4;

    public static <T extends OrderAttribute>
    ReplyKeyboard getKeyboardByParameter(T[] parameters, String placeholder) {
        final Class<? extends OrderAttribute> parameterClass = parameters[0].getClass();
        ReplyKeyboard keyboard = KEYBOARDS.get(parameterClass);

        if (keyboard == null) {
            keyboard = ReplyKeyboardMarkup.builder()
                    .keyboard(parametersRows(parameters))
                    .keyboardRow(NAVIGATION_ROW)
                    .inputFieldPlaceholder(placeholder)
                    .selective(true)
                    .resizeKeyboard(true)
                    .build();

            KEYBOARDS.put(parameterClass, keyboard);
        }

        return keyboard;
    }

    private static <T extends OrderAttribute>
    List<KeyboardRow> parametersRows(T[] parameters) {
        final AtomicInteger counter = new AtomicInteger();

        return toStream(parameters)
                .collect(Collectors.groupingBy(i -> counter.getAndIncrement() / GROUP_SIZE))
                .values()
                .stream()
                .map(KeyboardFactory::createKeyboardRow)
                .toList();
    }

    private static KeyboardRow createNavigationRow() {
        final List<String> buttons = toStream(NavigationButtons.values())
                .toList();
        return createKeyboardRow(buttons);
    }

    private static KeyboardRow createKeyboardRow(List<String> buttons) {
        final KeyboardRow row = new KeyboardRow();
        row.addAll(buttons);

        return row;
    }

    private static <T extends OrderAttribute>
    Stream<String> toStream(T[] elements) {
        return Arrays.stream(elements)
                .map(OrderAttribute::asString);
    }
}
