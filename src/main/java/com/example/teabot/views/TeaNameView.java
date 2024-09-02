package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import com.example.teabot.model.enums.tea.TeaName;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class TeaNameView implements StateView {

    @Override
    public String question() {
        return "Select or input tea name";
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(TeaName.values(), question());
    }
}
