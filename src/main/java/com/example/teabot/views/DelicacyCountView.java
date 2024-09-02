package com.example.teabot.views;

import com.example.teabot.handlers.StateView;
import com.example.teabot.model.enums.delicacy.DelicacyCount;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;

class DelicacyCountView implements StateView {

    @Override
    public String question() {
        return "Input delicacy count";
    }

    @Override
    public ReplyKeyboard getMarkup() {
        return KeyboardFactory.getKeyboardByParameter(delicacyCounts(), question());
    }

    private static DelicacyCount[] delicacyCounts() {
        return Arrays.stream(DelicacyCount.values())
                .filter(c -> c != DelicacyCount.ZERO)
                .toArray(DelicacyCount[]::new);
    }
}
