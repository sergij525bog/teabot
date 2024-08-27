package com.example.teabot.model.enums.cup;

import com.example.teabot.model.enums.OrderAttribute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum CupBuildingType implements OrderAttribute {
    BY_NAME("Input cup name"),
    BY_SIZE("Input cup size");

    private final String type;

    @Override
    public Stream<String> attributesAsStream() {
        return Arrays.stream(values())
                .map(CupBuildingType::getType);
    }
}
