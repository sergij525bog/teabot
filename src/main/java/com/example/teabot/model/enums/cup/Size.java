package com.example.teabot.model.enums.cup;

import com.example.teabot.model.enums.OrderParameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Size implements OrderParameter {
    SMALL("small"),
    MEDIUM("medium"),
    BIG("big"),
    NO_MATTER("no matter");

    private final String size;

    @Override
    public Stream<String> parametersAsStream() {
        return Arrays.stream(values())
                .map(Size::getSize);
    }
}
