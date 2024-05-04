package com.example.teabot.model.views;

import com.example.teabot.model.Delicacy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractDelicacyButtonView implements TeaButtonView {
    protected final Delicacy delicacy;
}
