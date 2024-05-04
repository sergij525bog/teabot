package com.example.teabot.model.views;

import com.example.teabot.model.Cup;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractCupButtonView implements TeaButtonView {
    protected final Cup cup;
}
