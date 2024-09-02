package com.example.teabot.model.enums.tea;

import com.example.teabot.model.enums.OrderAttribute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Additive implements OrderAttribute {
    LEMON("lemon"),
    RASPBERRY("raspberry"),
    GINGER("ginger"),
    HONEY("honey"),
    NONE("none");

    private final String additive;

    @Override
    public String toString() {
        return additive;
    }

    @Override
    public String asString() {
        return additive;
    }
}
