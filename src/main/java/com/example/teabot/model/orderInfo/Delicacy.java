package com.example.teabot.model.orderInfo;

import com.example.teabot.model.enums.delicacy.DelicacyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public final class Delicacy {
    private String type;
    private byte count;

    @Override
    public String toString() {
        String delicacyCount = count > 0 ? count + "" : null;

        Map<String, String> fields = new HashMap<>();
        fields.put("1type", type);
        fields.put("2count", delicacyCount);

        return fields.entrySet()
                .stream()
                .filter(e -> e.getValue() != null)
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey().substring(1) + ": " + e.getValue())
                .collect(Collectors.joining(", "));
    }

    public void setDefaults() {
        if (type == null) {
            type = DelicacyType.NONE.getType();
            count = 0;
        }
    }
}