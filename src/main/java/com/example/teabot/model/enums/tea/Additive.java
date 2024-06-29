package com.example.teabot.model.enums.tea;

import com.example.teabot.model.enums.OrderParameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Additive implements OrderParameter {
    LEMON("lemon"),
    RASPBERRY("raspberry"),
    GINGER("ginger"),
    HONEY("honey"),
    NONE("none");

    private final String additive;

    @Override
    public Stream<String> parametersAsStream() {
        return Arrays.stream(values())
                .map(Additive::getAdditive);
    }
}
