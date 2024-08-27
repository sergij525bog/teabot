package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import com.example.teabot.model.enums.cup.Name;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class CupNameView implements StateView {

    @Override
    public String question() {
        return "Input cup name";
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Name.BIG_BLUE, question());
    }
}
