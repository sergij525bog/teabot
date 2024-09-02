package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import com.example.teabot.model.enums.tea.Additive;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class AdditiveStateView implements StateView {

    @Override
    public String question() {
        return "Select tea additives";
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Additive.values(), question());
    }
}
