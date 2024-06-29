package com.example.teabot.handlers;

import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderParameter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class KeyboardFactory {
    private static final Map<Class<? extends OrderParameter>, ReplyKeyboard> keyboardMap = new HashMap<>();
    private static final KeyboardRow navigationRow = createNavigationRow();
    public static final int GROUP_SIZE = 4;

    public static <T extends OrderParameter> ReplyKeyboard getKeyboardByParameter(T parameter, String placeholder) {
        Class<? extends OrderParameter> parameterClass = parameter.getClass();
        ReplyKeyboard keyboard = keyboardMap.get(parameterClass);

        if (keyboard == null) {
            keyboard = ReplyKeyboardMarkup.builder()
                    .keyboard(parametersRows(parameter))
                    .keyboardRow(navigationRow)
                    .inputFieldPlaceholder(placeholder)
                    .selective(true)
                    .resizeKeyboard(true)
                    .build();

            keyboardMap.put(parameterClass, keyboard);
        }

        return keyboard;
    }

    private static <T extends OrderParameter> List<KeyboardRow> parametersRows(T parameter) {
        AtomicInteger counter = new AtomicInteger();

        return parameter
                .parametersAsStream()
                .collect(Collectors.groupingBy(i -> counter.getAndIncrement() / GROUP_SIZE))
                .values()
                .stream()
                .map(chunk -> {
                    KeyboardRow row = new KeyboardRow();
                    row.addAll(chunk);

                    return row;
                })
                .toList();
    }

    private static KeyboardRow createNavigationRow() {
        KeyboardRow navigationRow = new KeyboardRow();

        Arrays.stream(NavigationButtons.values())
                .map(NavigationButtons::getNavigation)
                .forEach(navigationRow::add);

        return navigationRow;
    }
}
