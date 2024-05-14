package com.example.teabot.model.enums.tea;

import com.example.teabot.model.enums.OrderParameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum TeaNames implements OrderParameter {
    MOJITO("mojito"),
    TRUSKAVKA("truskavka"),
    MELISSA("melissa"),
    LOVARE("lovare"),
    PICVIC("picvic");

    private final String teaName;

    @Override
    public Stream<String> parametersAsStream() {
        return Arrays.stream(values())
                .map(TeaNames::getTeaName);
    }
}
