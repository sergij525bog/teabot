package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import com.example.teabot.model.enums.tea.Color;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class ColorStateView implements StateView {

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Color.BLACK, question());
    }

    @Override
    public String question() {
        return "Select tea color";
    }
}
