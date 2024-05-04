package com.example.teabot.model.views;

import com.example.teabot.model.Cup;
import com.example.teabot.model.Tea;
import com.example.teabot.model.enums.cup.CupBuildingType;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;

public class CupBuildingTypeButtonView extends AbstractCupButtonView {
    public CupBuildingTypeButtonView(Cup cup) {
        super(cup);
    }

    @Override
    public String question() {
        return "What cup do you want?";
    }

    @Override
    public void updateTea(Tea tea, Update update) {

    }

    @Override
    public ReplyKeyboard getMarkup() {
        KeyboardRow types = new KeyboardRow();
        Arrays.stream(CupBuildingType.values())
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
