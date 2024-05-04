package com.example.teabot.model.views;

import com.example.teabot.model.Tea;
import com.example.teabot.model.enums.tea.TeaNames;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;

public class TeaNameButtonView extends AbstractTeaButtonView implements TeaButtonView {

    public TeaNameButtonView(Tea tea) {
        super(tea);
    }

    @Override
    public String question() {
        return "Select or input tea name";
    }

    @Override
    public void updateTea(Tea tea, Update update) {
        final String name = update.getMessage().getText();
        tea.setName(name);
    }

    @Override
    public ReplyKeyboard getMarkup() {
        final List<String> buttons = Arrays.stream(TeaNames.values())
                .map(TeaNames::getTeaName)
                .toList();

        KeyboardRow teaNamesRow = new KeyboardRow();
        teaNamesRow.addAll(buttons);

        KeyboardRow navigationRow = new KeyboardRow();
        navigationRow.add("back");

        return ReplyKeyboardMarkup.builder()
                .keyboardRow(teaNamesRow)
                .keyboardRow(navigationRow)
                .resizeKeyboard(true)
                .build();
    }
}
