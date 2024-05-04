package com.example.teabot.model.views;

import com.example.teabot.model.Cup;
import com.example.teabot.model.Tea;
import com.example.teabot.model.enums.cup.Name;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;

public class CupNameButtonView extends AbstractCupButtonView {

    public CupNameButtonView(Cup cup) {
        super(cup);
    }

    @Override
    public String question() {
        return "Input cup name";
    }

    @Override
    public void updateTea(Tea tea, Update update) {

    }

    @Override
    public ReplyKeyboard getMarkup() {
        KeyboardRow cupNames = new KeyboardRow();
        Arrays.stream(Name.values())
                .forEach(name -> cupNames.add(name.getName()));

        KeyboardRow navigationRow = new KeyboardRow();
        navigationRow.add("back");

        return ReplyKeyboardMarkup.builder()
                .keyboardRow(cupNames)
                .keyboardRow(navigationRow)
                .resizeKeyboard(true)
                .build();
    }
}
