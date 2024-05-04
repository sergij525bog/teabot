package com.example.teabot.model.enums.cup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CupBuildingType {
    BY_NAME("Input cup name"),
    BY_SIZE("Input cup size");

    private final String type;
}
