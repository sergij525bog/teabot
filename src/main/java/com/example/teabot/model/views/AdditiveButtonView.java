package com.example.teabot.model.views;

import com.example.teabot.model.Tea;
import com.example.teabot.model.enums.tea.Additive;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;

public class AdditiveButtonView extends AbstractTeaButtonView implements TeaButtonView {

    public AdditiveButtonView(Tea tea) {
        super(tea);
    }

    @Override
    public ReplyKeyboard getMarkup() {
        KeyboardRow additivesRow = new KeyboardRow();
        Arrays.stream(Additive.values())
                .forEach(additive -> additivesRow.add(additive.getAdditive()));

        KeyboardRow navigationRow = new KeyboardRow();
        navigationRow.addAll(List.of("back", "next"));

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(additivesRow, navigationRow))
//                .selective(true)
                .resizeKeyboard(true)
                .build();
    }

    @Override
    public String question() {
        return "Select tea additives";
    }

    @Override
    public void updateTea(Tea tea, Update update) {
        final String data = update.getCallbackQuery().getData();
        final Additive additive = Additive.getInstanceByString(data);

        if (additive == Additive.NONE) {
            tea.getAdditives().clear();
        } else {
            tea.getAdditives().add(additive);
        }
    }
}
