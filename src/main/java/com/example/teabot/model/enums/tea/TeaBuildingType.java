package com.example.teabot.model.enums.tea;

import com.example.teabot.model.enums.OrderAttribute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum TeaBuildingType implements OrderAttribute {
    BY_NAME("Input tea name"),
    BY_DESCRIPTION("Describe tea");

    private final String value;

    @Override
    public Stream<String> attributesAsStream() {
        return Arrays.stream(values())
                .map(TeaBuildingType::getValue);
    }

    @Override
    public String toString() {
        return value;
    }
}
