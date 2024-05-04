package com.example.teabot.model.enums.tea;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TeaNames {
    MOJITO("mojito"),
    TRUSKAVKA("truskavka"),
    MELISSA("melissa"),
    LOVARE("lovare"),
    PICVIC("picvic");

    private final String teaName;
}
