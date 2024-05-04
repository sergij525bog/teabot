package com.example.teabot.model;

import com.example.teabot.model.enums.tea.Additive;
import com.example.teabot.model.enums.tea.Color;
import com.example.teabot.model.enums.tea.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@NoArgsConstructor
@Setter
@Getter
public final class Tea {
    private String name;
    private Color color;
    private Type type;
    private final List<Additive> additives = new LinkedList<>();

    @Override
    public String toString() {
        final String teaColor = color != null ? color.getColor() : null;
        final String teaType = type != null ? type.getType() : null;
        return  "name: '" + name + '\'' +
                ", color: " + teaColor +
                ", type: " + teaType +
                ", additives: " + additives.stream().map(Additive::getAdditive).collect(Collectors.joining(", "));
    }
}
