package com.example.teabot.model.enums.delicacy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DelicacyType {
    COOKIE("cookie"),
    CANDY("candy"),
    CAKE("cake"),
    ROLL("roll"),
    WAFFLE("waffle"),
    CUPCAKE("cupcake"),
    PANCAKE("pancake"),
    NONE("none");

    private final String type;
}
