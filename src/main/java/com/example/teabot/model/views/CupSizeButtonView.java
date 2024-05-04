package com.example.teabot.model.views;

import com.example.teabot.model.Cup;
import com.example.teabot.model.Tea;
import com.example.teabot.model.enums.cup.Size;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;

public class CupSizeButtonView extends AbstractCupButtonView {
    public CupSizeButtonView(Cup cup) {
        super(cup);
    }

    @Override
    public String question() {
        return "input cup size";
    }

    @Override
    public void updateTea(Tea tea, Update update) {

    }

    @Override
    public ReplyKeyboard getMarkup() {
        KeyboardRow cupSizes = new KeyboardRow();
        Arrays.stream(Size.values())
                .forEach(size -> cupSizes.add(size.getSize()));

        KeyboardRow navigationRow = new KeyboardRow();
        navigationRow.add("back");

        return ReplyKeyboardMarkup.builder()
                .keyboardRow(cupSizes)
                .keyboardRow(navigationRow)
                .resizeKeyboard(true)
                .build();
    }
}
