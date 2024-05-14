package com.example.teabot.model.enums.tea;

import com.example.teabot.model.enums.OrderParameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Color implements OrderParameter {
    GREEN("green"),
    BLACK("black"),
    NO_MATTER("no matter");

    private final String color;

    public static Color getInstanceByString(String color) {
        return Arrays.stream(values())
                .filter(e -> e.color.equals(color))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public Stream<String> parametersAsStream() {
        return Arrays.stream(values())
                .map(Color::getColor);
    }
}
