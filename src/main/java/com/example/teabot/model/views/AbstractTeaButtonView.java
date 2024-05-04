package com.example.teabot.model.views;

import com.example.teabot.model.Tea;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractTeaButtonView implements View {
    protected final Tea tea;
}
