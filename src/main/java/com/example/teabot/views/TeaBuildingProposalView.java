package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import com.example.teabot.model.enums.tea.TeaBuildingType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class TeaBuildingProposalView implements StateView {

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(TeaBuildingType.BY_NAME, question());
    }

    @Override
    public String question() {
        return "What tea do you want?";
    }
}
