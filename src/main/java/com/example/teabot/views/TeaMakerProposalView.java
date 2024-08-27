package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import com.example.teabot.model.enums.teamaker.MakerSelectingProposals;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

class TeaMakerProposalView implements StateView {

    @Override
    public String question() {
        return "You want to create tea or ask someone to do it?";
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(MakerSelectingProposals.I_WANT_TEA, question());
    }
}
