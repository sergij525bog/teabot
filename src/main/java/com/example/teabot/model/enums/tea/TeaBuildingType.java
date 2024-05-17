package com.example.teabot.model.enums.tea;

import com.example.teabot.model.enums.OrderParameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum TeaBuildingType implements OrderParameter {
    BY_NAME("Input tea name"),
    BY_DESCRIPTION("Describe tea");

    private final String buildingType;

    @Override
    public Stream<String> parametersAsStream() {
        return Arrays.stream(values())
                .map(TeaBuildingType::getBuildingType);
    }
}
