package com.example.teabot.model.enums;

public enum OrderState {
    START,

    TEA_MAKER_BUILDING_PROPOSAL,
    TEA_BUILDING_TYPE_PROPOSAL,
    TEA_BUILDING_TYPE_AWAITING,
    INPUT_NAME_AWAITING,
    TYPE_SELECTION_AWAITING,
    COLOR_SELECTION_AWAITING,
    ADDITIONS_AWAITING,

    CUP_BUILDING_TYPE_PROPOSAL,
    CUP_BUILDING_TYPE_AWAITING,
    CUP_SIZE_AWAITING,
    CUP_NAME_AWAITING,

    DELICACY_TYPE_AWAITING,
    DELICACY_COUNT_AWAITING,

    SAVE_ORDER_AWAITING,
    CANCEL_ORDER,
    WITHOUT_ORDER,

    ERROR,
    NULL;

    public static boolean isFinalState(OrderState state) {
        return state == SAVE_ORDER_AWAITING ||
                state == CANCEL_ORDER ||
                state == WITHOUT_ORDER;
    }
}
