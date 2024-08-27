package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

interface FinalStateView extends StateView {
    @Override
    default ReplyKeyboard getMarkup() {
        return ReplyKeyboardRemove.builder()
                .removeKeyboard(true)
                .selective(true)
                .build();
    }
}
