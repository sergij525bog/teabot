package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class StartView implements StateView {

    @Override
    public ReplyKeyboard getMarkup() {
        return null;
    }

    @Override
    public String question() {
        return "You want to create tea or ask someone to do it?";
    }
}
