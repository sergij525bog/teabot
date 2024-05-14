package com.example.teabot.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum NavigationButtons {
    NEXT("next"),
    BACK("back"),
    CANCEL("cancel"),
    SKIP("skip and order");

    private final String navigation;

    @Override
    public String toString() {
        return navigation;
    }

    public static boolean isNavigation(String data) {
        return Arrays.stream(NavigationButtons.values())
                .map(NavigationButtons::getNavigation)
                .anyMatch((navigation -> navigation.equals(data)));
    }

    public static NavigationButtons getInstance(String s) {
        return Arrays.stream(values())
                .filter(b -> b.navigation.equals(s))
                .findFirst()
                .orElseThrow();
    }
}
