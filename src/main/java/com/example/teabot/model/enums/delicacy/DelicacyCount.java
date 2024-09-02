package com.example.teabot.model.enums.delicacy;

import com.example.teabot.model.enums.OrderAttribute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum DelicacyCount implements OrderAttribute {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int value;

//    @Override
//    public Stream<String> attributesAsStream() {
//        return Stream.of(values())
//                .filter(v -> v != ZERO)
//                .map(DelicacyCount::getValue)
//                .map(String::valueOf);
//    }

    @Override
    public String asString() {
        return String.valueOf(value);
    }
}
