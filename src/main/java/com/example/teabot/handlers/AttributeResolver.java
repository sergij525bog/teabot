package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderAttribute;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.StartCommand;
import com.example.teabot.model.enums.cup.CupBuildingType;
import com.example.teabot.model.enums.cup.CupName;
import com.example.teabot.model.enums.cup.CupSize;
import com.example.teabot.model.enums.delicacy.DelicacyCount;
import com.example.teabot.model.enums.delicacy.DelicacyType;
import com.example.teabot.model.enums.tea.*;
import com.example.teabot.model.enums.teamaker.MakerSelectingProposals;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.teabot.model.enums.OrderState.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AttributeResolver {
    private static final Map<OrderState, Map<String, ? extends OrderAttribute>> ATTRIBUTES = createMap();

    public static Optional<? extends OrderAttribute> resolve(OrderState state, String attribute) {
        return Optional.ofNullable(ATTRIBUTES.get(state))
                .map(m -> m.get(attribute));
    }

    private static Map<OrderState, Map<String, ? extends OrderAttribute>> createMap() {
        final Map<OrderState, Map<String, ? extends OrderAttribute>> map = new EnumMap<>(OrderState.class);

        map.put(START, mapFromEnum(StartCommand.values(), StartCommand::getValue));
        map.put(TEA_MAKER_BUILDING_PROPOSAL, mapFromEnum(
                MakerSelectingProposals.values(),
                MakerSelectingProposals::getMessage));
        map.put(TEA_BUILDING_TYPE_PROPOSAL, mapFromEnum(TeaBuildingType.values(), TeaBuildingType::getValue));
        map.put(INPUT_NAME_AWAITING, mapFromEnum(TeaName.values(), TeaName::getValue));
        map.put(TYPE_SELECTION_AWAITING, mapFromEnum(Type.values(), Type::getType));
        map.put(COLOR_SELECTION_AWAITING, mapFromEnum(Color.values(), Color::getColor));
        map.put(ADDITIONS_AWAITING, mapFromEnum(Additive.values(), Additive::getAdditive));
        map.put(CUP_BUILDING_TYPE_PROPOSAL, mapFromEnum(CupBuildingType.values(), CupBuildingType::getType));
        map.put(CUP_NAME_AWAITING, mapFromEnum(CupName.values(), CupName::getName));
        map.put(CUP_SIZE_AWAITING, mapFromEnum(CupSize.values(), CupSize::getSize));
        map.put(DELICACY_TYPE_AWAITING, mapFromEnum(DelicacyType.values(), DelicacyType::getType));
        map.put(DELICACY_COUNT_AWAITING, mapFromEnum(delicacyCounts(), e -> String.valueOf(e.getValue())));

        return map;
    }

    private static <T extends OrderAttribute> Map<String, ? extends OrderAttribute> mapFromEnum(
            T[] values, Function<T, String> getter
    ) {
        return Arrays.stream(values)
                .collect(Collectors.toMap(getter, e -> e));
    }

    private static DelicacyCount[] delicacyCounts() {
        return Arrays.stream(DelicacyCount.values())
                .filter(c -> c != DelicacyCount.ZERO)
                .toArray(DelicacyCount[]::new);
    }
}
