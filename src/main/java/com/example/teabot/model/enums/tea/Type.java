package com.example.teabot.model.enums.tea;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Type {
    TEA_IN_BAGS("tea in bags"),
    LEAF_TEA("leaf tea"),
    NO_MATTER("no matter");

    private final String type;

    public static Type getInstanceByString(String color) {
        return Arrays.stream(values())
                .filter(e -> e.type.equals(color))
                .findFirst()
                .orElseThrow();
    }
}