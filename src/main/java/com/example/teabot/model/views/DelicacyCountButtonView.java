package com.example.teabot.model.views;

import com.example.teabot.model.Delicacy;
import com.example.teabot.model.Tea;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.stream.IntStream;

public class DelicacyCountButtonView extends AbstractDelicacyButtonView {
    public DelicacyCountButtonView(Delicacy delicacy) {
        super(delicacy);
    }

    @Override
    public String question() {
        return "Input delicacy count";
    }

    @Override
    public void updateTea(Tea tea, Update update) {

    }

    @Override
    public ReplyKeyboard getMarkup() {
        KeyboardRow counts = new KeyboardRow();
        IntStream.iterate(1, i -> i + 1)
                .limit(5)
                .mapToObj(String::valueOf)
                .forEach(counts::add);

        KeyboardRow navigationRow = new KeyboardRow();
        navigationRow.add("back");

        return ReplyKeyboardMarkup.builder()
                .keyboardRow(counts)
                .keyboardRow(navigationRow)
                .resizeKeyboard(true)
                .build();
    }
}
