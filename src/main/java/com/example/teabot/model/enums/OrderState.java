package com.example.teabot.model.enums;

public enum OrderState {
    START,
    TEA_MAKER_BUILDING_PROPOSAL,
//    TEA_MAKER_SELECTING_AWAITING,
    TEA_BUILDING_TYPE_PROPOSAL,
    TEA_BUILDING_TYPE_AWAITING,
    INPUT_NAME_AWAITING,
    TYPE_SELECTION_AWAITING,
    COLOR_SELECTION_AWAITING,
    ADDITIONS_AWAITING,
    SAVE_ORDER_AWAITING,
    CUP_BUILDING_TYPE_PROPOSAL,
    CUP_BUILDING_TYPE_AWAITING,
    CUP_SIZE_AWAITING,
    CUP_NAME_AWAITING,

    DELICACY_TYPE_AWAITING,
    DELICACY_COUNT_AWAITING,

    SAVE_ORDER,
    NULL,
    ERROR,
    CANCEL_ORDER,
    SKIP_AND_ORDER,
    WITHOUT_ORDER;

    public static boolean isFinalState(OrderState newState) {
        return newState == OrderState.SAVE_ORDER_AWAITING ||
                newState == OrderState.CANCEL_ORDER ||
                newState == OrderState.WITHOUT_ORDER;
    }
}
