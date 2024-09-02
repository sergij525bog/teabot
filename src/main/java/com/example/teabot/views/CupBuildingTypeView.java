package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import com.example.teabot.model.enums.cup.CupBuildingType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class CupBuildingTypeView implements StateView {

    @Override
    public String question() {
        return "What cup do you want?";
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(CupBuildingType.values(), question());
    }
}
