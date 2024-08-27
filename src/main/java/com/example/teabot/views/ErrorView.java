package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@RequiredArgsConstructor
class ErrorView implements StateView {
    private final StateView lastWorkedHandler;

    @Override
    public String question() {
        return "Unexpected input. Please try again";
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return lastWorkedHandler.getMarkup();
    }
}
