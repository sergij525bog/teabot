package com.example.teabot.model.views;

import com.example.teabot.model.Tea;
import com.example.teabot.model.enums.tea.Type;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;

public class TeaTypeButtonView extends AbstractTeaButtonView implements TeaButtonView {

    public TeaTypeButtonView(Tea tea) {
        super(tea);
    }

    @Override
    public ReplyKeyboard getMarkup() {
        KeyboardRow teaTypesRow = new KeyboardRow();
        Arrays.stream(Type.values())
                .forEach(additive -> teaTypesRow.add(additive.getType()));

        KeyboardRow navigationRow = new KeyboardRow();
        navigationRow.add("back");

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(teaTypesRow, navigationRow))
                .resizeKeyboard(true)
                .selective(true)
                .build();

//        final Type[] values = Type.values();
//        List<InlineKeyboardButton> buttons = Arrays.stream(values)
//                .map(Type::getType)
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
        return "Select tea type";
    }

    @Override
    public void updateTea(Tea tea, Update update) {
        final String data = update.getCallbackQuery().getData();
        tea.setType(Type.getInstanceByString(data));
    }

    private InlineKeyboardButton getButton(String buttonData) {
        return InlineKeyboardButton.builder()
                .text(buttonData)
                .callbackData(buttonData)
                .build();
    }
}
