package com.example.teabot.model.views;

import com.example.teabot.model.Delicacy;
import com.example.teabot.model.Tea;
import com.example.teabot.model.enums.delicacy.DelicacyType;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;

public class DelicacyTypeButtonView extends AbstractDelicacyButtonView {
    public DelicacyTypeButtonView(Delicacy delicacy) {
        super(delicacy);
    }

    @Override
    public String question() {
        return "Input delicacy type";
    }

    @Override
    public void updateTea(Tea tea, Update update) {

    }

    @Override
    public ReplyKeyboard getMarkup() {
        KeyboardRow types = new KeyboardRow();
        Arrays.stream(DelicacyType.values())
                .forEach(type -> types.add(type.getType()));

        KeyboardRow navigationRow = new KeyboardRow();
        navigationRow.add("back");

        return ReplyKeyboardMarkup.builder()
                .keyboardRow(types)
                .keyboardRow(navigationRow)
                .resizeKeyboard(true)
                .build();
    }
}
