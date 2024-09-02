package com.example.teabot.model.enums.cup;

import com.example.teabot.model.enums.OrderAttribute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum CupName implements OrderAttribute {
    OPTIMIST("optimist"),
    BIG_BLUE("big blue"),
    BIG_GRAY("big gray"),
    TERMO("termo"),
    TURQUOISE("biryuza"),
    BLACK("black"),
    GLASS_SMALL("small glass"),
    GLASS_BIG("big glass"),
    NO_MATTER("no matter");

    private final String name;

    @Override
    public Stream<String> attributesAsStream() {
        return Arrays.stream(values())
                .map(CupName::getName);
    }
}
