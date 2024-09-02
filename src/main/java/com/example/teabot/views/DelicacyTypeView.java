package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import com.example.teabot.model.enums.delicacy.DelicacyType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class DelicacyTypeView implements StateView {

    @Override
    public String question() {
        return "Input delicacy type";
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(DelicacyType.values(), question());
    }
}
