package com.example.teabot.model.enums.cup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Size {
    SMALL("small"),
    MEDIUM("medium"),
    BIG("big"),
    NO_MATTER("no matter");

    private final String size;
}
