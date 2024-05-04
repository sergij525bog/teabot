package com.example.teabot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public final class Cup {
    private String name;
    private String size;

    @Override
    public String toString() {
        return "name: '" + name + '\'' +
                ", size: '" + size + '\'';
    }
}
