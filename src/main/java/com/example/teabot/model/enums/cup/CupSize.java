package com.example.teabot.model.enums.cup;

import com.example.teabot.model.enums.OrderAttribute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum CupSize implements OrderAttribute {
    SMALL("small"),
    MEDIUM("medium"),
    BIG("big"),
    NO_MATTER("no matter");

    private final String size;

    @Override
    public String asString() {
        return size;
    }
}
