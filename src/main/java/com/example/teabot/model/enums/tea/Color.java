package com.example.teabot.model.enums.tea;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Color {
    GREEN("green"),
    BLACK("black"),
    NO_MATTER("no matter");

    private final String color;

    public static Color getInstanceByString(String color) {
        return Arrays.stream(values())
                .filter(e -> e.color.equals(color))
                .findFirst()
                .orElseThrow();
    }
}
