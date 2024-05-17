package com.example.teabot.model.enums.delicacy;

import com.example.teabot.model.enums.OrderParameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum DelicacyType implements OrderParameter {
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
    public Stream<String> parametersAsStream() {
        return Arrays.stream(values())
                .map(DelicacyType::getType);
    }
}
