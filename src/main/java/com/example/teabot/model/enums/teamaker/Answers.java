package com.example.teabot.model.enums.teamaker;

import com.example.teabot.model.enums.OrderParameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Answers implements OrderParameter {
    I_CAN_CREATE_TEA("Yes"),
    I_CANNOT_CREATE_TEA("No");

    private final String answer;

    @Override
    public Stream<String> parametersAsStream() {
        return Arrays.stream(values())
                .map(Answers::getAnswer);
    }
}
