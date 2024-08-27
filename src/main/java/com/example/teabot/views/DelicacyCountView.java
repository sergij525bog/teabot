package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import com.example.teabot.model.enums.delicacy.DelicacyCount;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class DelicacyCountView implements StateView {

    @Override
    public String question() {
        return "Input delicacy count";
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(DelicacyCount.ONE, question());
    }
}
