package com.example.teabot.model.views;

import com.example.teabot.model.Tea;
import com.example.teabot.model.enums.tea.TeaBuildingType;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;

public class InitialView extends AbstractTeaButtonView implements TeaButtonView {

    public InitialView(Tea tea) {
        super(tea);
    }

    @Override
    public ReplyKeyboard getMarkup() {
        final List<String> buttons = Arrays.stream(TeaBuildingType.values())
                .map(TeaBuildingType::getBuildingType)
                .toList();

        KeyboardRow row = new KeyboardRow();
        row.addAll(buttons);
        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(row))
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true)
                .build();
    }

    @Override
    public String question() {
        return "What tea do you want?";
    }

    @Override
    public void updateTea(Tea tea, Update update) {
    }
}
