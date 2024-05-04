package com.example.teabot.model.enums.tea;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TeaBuildingType {
    BY_NAME("Input tea name"),
    BY_DESCRIPTION("Describe tea");

    private final String buildingType;
}
