package com.example.teabot.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderState {
    START(true),

    TEA_MAKER_BUILDING_PROPOSAL(false),
    TEA_BUILDING_TYPE_PROPOSAL(true),
    INPUT_NAME_AWAITING(false),
    TYPE_SELECTION_AWAITING(false),
    COLOR_SELECTION_AWAITING(false),
    ADDITIONS_AWAITING(false),

    CUP_BUILDING_TYPE_PROPOSAL(true),
    CUP_SIZE_AWAITING(false),
    CUP_NAME_AWAITING(false),

    DELICACY_TYPE_AWAITING(false),
    DELICACY_COUNT_AWAITING(false),

    SAVE_ORDER_AWAITING(true),
    CANCEL_ORDER(true),
    WITHOUT_ORDER(true),

    ERROR(true),
    NULL(true);

    private final boolean isNeutralForOrder;

    public static boolean isFinal(OrderState state) {
        return state == SAVE_ORDER_AWAITING ||
                state == CANCEL_ORDER ||
                state == WITHOUT_ORDER;
    }
}
