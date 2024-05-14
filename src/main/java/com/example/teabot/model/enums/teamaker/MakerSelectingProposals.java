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
    I_WILL_MAKE_TEA("I will make tea");

    private final String message;

    @Override
    public Stream<String> parametersAsStream() {
        return Arrays.stream(values())
                .map(MakerSelectingProposals::getMessage);
    }
}
