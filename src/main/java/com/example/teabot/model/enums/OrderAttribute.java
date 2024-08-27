package com.example.teabot.model.enums;

import java.util.List;
import java.util.stream.Stream;

@FunctionalInterface
public interface OrderAttribute {

    Stream<String> attributesAsStream();

    default List<String> attributes() {
        return attributesAsStream().toList();
    }
}
