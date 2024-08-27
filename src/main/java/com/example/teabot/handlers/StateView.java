package com.example.teabot.handlers;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface StateView {
    String question();

    ReplyKeyboard getMarkup();
}
