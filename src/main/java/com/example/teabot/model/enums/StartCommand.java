package com.example.teabot.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum StartCommand implements OrderAttribute {
    START("/start"),
    START_TEA_BOT("/start@YouAreTheTea_bot");

    private final String value;

    @Override
    public Stream<String> attributesAsStream() {
        return Stream.of(values())
                .map(StartCommand::getValue);
    }
}
