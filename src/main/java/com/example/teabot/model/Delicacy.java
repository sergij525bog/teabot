package com.example.teabot.model;

import com.example.teabot.model.enums.delicacy.DelicacyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public final class Delicacy {
    private DelicacyType type;
    private byte count;

    @Override
    public String toString() {
        return "type: " + type.getType() +
                ", count: " + count;
    }
}