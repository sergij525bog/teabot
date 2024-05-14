package com.example.teabot.model.handlers;

import com.example.teabot.model.ChatInfo;
import com.example.teabot.model.enums.AttributeUpdateStatus;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface AttributeHandler {
    String question();

    AttributeUpdateStatus updateAttribute(String data, ChatInfo orderInfo);

    ReplyKeyboard getMarkup();
}
