package com.example.teabot.model.enums.tea;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Additive {
    LEMON("lemon"),
    RASPBERRY("raspberry"),
    GINGER("ginger"),
    HONEY("honey"),
    NONE("none");

    private final String additive;

    public static Additive getInstanceByString(String color) {
        return Arrays.stream(values())
                .filter(e -> e.additive.equals(color))
                .findFirst()
                .orElseThrow();
    }
}