package com.example.teabot.model;

import com.example.teabot.model.enums.tea.Additive;
import com.example.teabot.model.enums.tea.Color;
import com.example.teabot.model.enums.tea.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
    private final Set<Additive> additives = new HashSet<>();

    @Override
    public String toString() {
        final String teaColor = color != null ? color.getColor() : null;
        final String teaType = type != null ? type.getType() : null;
        final String teaAdditives = additives.stream()
                .map(Additive::getAdditive)
                .collect(Collectors.joining(", "));

        Map<String, String> fields = new HashMap<>();
        fields.put("1name", name);
        fields.put("2type", teaType);
        fields.put("3color", teaColor);
        fields.put("4additives", teaAdditives);

        return fields.entrySet()
                .stream()
                .filter(e -> e.getValue() != null)
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey().substring(1) + ": " + e.getValue())
                .collect(Collectors.joining(", "));
    }

    public void setDefaults() {
        if (name == null && color == null) {
            color = Color.GREEN;
        }
        if (name == null && type == null) {
            type = Type.TEA_IN_BAGS;
        }
    }
}
