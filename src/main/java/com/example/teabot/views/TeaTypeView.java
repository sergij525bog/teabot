package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import com.example.teabot.model.enums.tea.Type;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class TeaTypeView implements StateView {

    @Override
    public String question() {
        return "Select tea type";
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Type.LEAF_TEA, question());
    }
}
