package com.example.teabot.model.orderInfo;

import com.example.teabot.model.enums.cup.CupName;
import com.example.teabot.model.enums.cup.CupSize;
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
    private CupName cupName;
    private String size;

    @Override
    public String toString() {
        Map<String, String> fields = new HashMap<>();
        if (cupName != null) {
            fields.put("1name", cupName.getName());
        }

        fields.put("2size", size);

        return fields.entrySet()
                .stream()
                .filter(e -> e.getValue() != null)
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey().substring(1) + ": " + e.getValue())
                .collect(Collectors.joining(", "));
    }

    public void setDefaults() {
        if (cupName == null) {
            cupName = CupName.OPTIMIST;
        } else if (size == null) {
            size = CupSize.BIG.getSize();
        }
    }
}
