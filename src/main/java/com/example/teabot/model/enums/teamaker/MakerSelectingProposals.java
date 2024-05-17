package com.example.teabot.model.enums.teamaker;

import com.example.teabot.model.enums.OrderParameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum MakerSelectingProposals implements OrderParameter {
    I_WANT_TEA("I want tea"),
    I_CAN_MAKE_TEA("I can make tea"),
    I_WANT_TEA_AND_CAN_MAKE_IT("I will make tea for myself and can make for others");

    private final String message;

    @Override
    public Stream<String> parametersAsStream() {
        return Arrays.stream(values())
                .map(MakerSelectingProposals::getMessage);
    }
}
