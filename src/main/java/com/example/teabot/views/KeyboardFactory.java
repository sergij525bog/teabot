package com.example.teabot.views;

import com.example.teabot.model.enums.NavigationButtons;
import com.example.teabot.model.enums.OrderAttribute;
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
    private static final Map<Class<? extends OrderAttribute>, ReplyKeyboard> keyboardMap = new HashMap<>();
    private static final KeyboardRow navigationRow = createNavigationRow();
    public static final int GROUP_SIZE = 4;

    public static <T extends OrderAttribute> ReplyKeyboard getKeyboardByParameter(T parameter, String placeholder) {
        final Class<? extends OrderAttribute> parameterClass = parameter.getClass();
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

    private static <T extends OrderAttribute> List<KeyboardRow> parametersRows(T parameter) {
        final AtomicInteger counter = new AtomicInteger();

        return parameter
                .attributesAsStream()
                .collect(Collectors.groupingBy(i -> counter.getAndIncrement() / GROUP_SIZE))
                .values()
                .stream()
                .map(chunk -> {
                    final KeyboardRow row = new KeyboardRow();
                    row.addAll(chunk);

                    return row;
                })
                .toList();
    }

    private static KeyboardRow createNavigationRow() {
        final KeyboardRow navigationRow = new KeyboardRow();

        Arrays.stream(NavigationButtons.values())
                .map(NavigationButtons::getNavigation)
                .forEach(navigationRow::add);

        return navigationRow;
    }
}
