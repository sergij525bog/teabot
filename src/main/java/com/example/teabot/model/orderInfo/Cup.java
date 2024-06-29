package com.example.teabot.model.orderInfo;

import com.example.teabot.model.enums.cup.Name;
import com.example.teabot.model.enums.cup.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public final class Cup {
    private String name;
    private String size;

    @Override
    public String toString() {
        Map<String, String> fields = new HashMap<>();
        fields.put("1name", name);
        fields.put("2size", size);

        return fields.entrySet()
                .stream()
                .filter(e -> e.getValue() != null)
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey().substring(1) + ": " + e.getValue())
                .collect(Collectors.joining(", "));
    }

    public void setDefaults() {
        if (name == null) {
            name = Name.OPTIMIST.getName();
        } else if (size == null) {
            size = Size.BIG.getSize();
        }
    }
}
