package com.example.teabot.model.enums.tea;

import com.example.teabot.model.enums.OrderAttribute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Type implements OrderAttribute {
    TEA_IN_BAGS("tea in bags"),
    LEAF_TEA("leaf tea"),
    NO_MATTER("no matter");

    private final String type;

    @Override
    public Stream<String> attributesAsStream() {
        return Arrays.stream(values())
                .map(Type::getType);
    }

    @Override
    public String toString() {
        return type;
    }
}