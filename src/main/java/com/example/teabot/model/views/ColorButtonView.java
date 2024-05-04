package com.example.teabot.model.views;

import com.example.teabot.model.Tea;
import com.example.teabot.model.enums.tea.Color;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;

public class ColorButtonView extends AbstractTeaButtonView implements TeaButtonView {

    public ColorButtonView(Tea tea) {
        super(tea);
    }

    @Override
    public ReplyKeyboard getMarkup() {
        KeyboardRow colorsRow = new KeyboardRow();
        Arrays.stream(Color.values())
                .forEach(additive -> colorsRow.add(additive.getColor()));

        KeyboardRow navigationRow = new KeyboardRow();
        navigationRow.add("back");

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(colorsRow, navigationRow))
                .resizeKeyboard(true)
                .selective(true)
                .build();

//        final Color[] values = Color.values();
//        List<InlineKeyboardButton> buttons = Arrays.stream(values)
//                .map(Color::getColor)
//                .map(this::getButton)
//                .toList();
//
//        return InlineKeyboardMarkup.builder()
//                .keyboardRow(buttons)
//                .keyboardRow(List.of(getButton("back")))
//                .build();
    }

    @Override
    public String question() {
        return "Select tea color";
    }

    @Override
    public void updateTea(Tea tea, Update update) {
        final String data = update.getCallbackQuery().getData();
        tea.setColor(Color.getInstanceByString(data));
    }

    private InlineKeyboardButton getButton(String buttonData) {
        return InlineKeyboardButton.builder()
                .text(buttonData)
                .callbackData(buttonData)
                .build();
    }
}
