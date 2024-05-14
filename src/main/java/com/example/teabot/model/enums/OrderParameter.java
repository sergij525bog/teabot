package com.example.teabot.model.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
public interface OrderParameter {

    Stream<String> parametersAsStream();

    default List<String> parameters() {
        return parametersAsStream().collect(Collectors.toList());
    }

    default boolean isParam(String data) {
        return parameters().contains(data);
    }
}
