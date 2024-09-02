package com.example.teabot.model.enums.tea;

import com.example.teabot.model.enums.OrderAttribute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Color implements OrderAttribute {
    GREEN("green"),
    BLACK("black"),
    NO_MATTER("no matter");

    private final String color;

    @Override
    public String toString() {
        return color;
    }

    @Override
    public String asString() {
        return color;
    }
}
