package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import com.example.teabot.model.enums.cup.Size;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class CupSizeView implements StateView {

    @Override
    public String question() {
        return "input cup size";
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(Size.BIG, question());
    }
}
