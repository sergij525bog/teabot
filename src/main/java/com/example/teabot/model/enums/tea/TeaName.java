package com.example.teabot.model.enums.tea;

import com.example.teabot.model.enums.OrderAttribute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum TeaName implements OrderAttribute {
    MOJITO("mojito"),
    TRUSKAVKA("truskavka"),
    MELISSA("melissa"),
    LOVARE("lovare"),
    PICVIC("picvic");

    private final String value;
//
//    @Override
//    public Stream<String> attributesAsStream() {
//        return Arrays.stream(values())
//                .map(TeaName::getValue);
//    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public String asString() {
        return value;
    }
}
