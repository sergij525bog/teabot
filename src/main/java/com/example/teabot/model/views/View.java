package com.example.teabot.model.views;

import com.example.teabot.model.Tea;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface View {
    String question();

    void updateTea(Tea tea, Update update);

    ReplyKeyboard getMarkup();
}
