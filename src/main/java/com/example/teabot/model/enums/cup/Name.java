package com.example.teabot.model.enums.cup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Name {
    OPTIMIST("optimist"),
    BIG_BLUE("big blue"),
    BIG_GRAY("big gray"),
    TERMO("termo"),
    TURQUOISE("biryuza"),
    BLACK("black"),
    GLASS_SMALL("small glass"),
    GLASS_BIG("big glass"),
    NO_MATTER("no matter");

    private final String name;
}
