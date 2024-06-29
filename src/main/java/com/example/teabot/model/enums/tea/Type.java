package com.example.teabot.model.enums.tea;

import com.example.teabot.model.enums.OrderParameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Type implements OrderParameter {
    TEA_IN_BAGS("tea in bags"),
    LEAF_TEA("leaf tea"),
    NO_MATTER("no matter");

    private final String type;

    @Override
    public Stream<String> parametersAsStream() {
        return Arrays.stream(values())
                .map(Type::getType);
    }
}