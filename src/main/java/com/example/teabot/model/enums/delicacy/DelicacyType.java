package com.example.teabot.model.enums.delicacy;

import com.example.teabot.model.enums.OrderAttribute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum DelicacyType implements OrderAttribute {
    COOKIE("cookie"),
    CANDY("candy"),
    CAKE("cake"),
    ROLL("roll"),
    WAFFLE("waffle"),
    CUPCAKE("cupcake"),
    PANCAKE("pancake"),
    NONE("none");

    private final String type;

    @Override
    public Stream<String> attributesAsStream() {
        return Arrays.stream(values())
                .map(DelicacyType::getType);
    }
}
