package com.example.teabot.handlers;

import com.example.teabot.model.enums.OrderAttribute;
import com.example.teabot.model.enums.OrderState;
import com.example.teabot.model.enums.StartCommand;
import com.example.teabot.model.enums.cup.CupBuildingType;
import com.example.teabot.model.enums.cup.Name;
import com.example.teabot.model.enums.cup.Size;
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
    private static final Map<OrderState, Map<String, ? extends OrderAttribute>> map = createMap();

    private static Map<OrderState, Map<String, ? extends OrderAttribute>> createMap() {
        EnumMap<OrderState, Map<String, ? extends OrderAttribute>> map = new EnumMap<>(OrderState.class);

        map.put(START, createMapFromEnum(StartCommand.values(), StartCommand::getValue));
        map.put(TEA_MAKER_BUILDING_PROPOSAL, createMapFromEnum(MakerSelectingProposals.values(), MakerSelectingProposals::getMessage));
        map.put(TEA_BUILDING_TYPE_PROPOSAL, createMapFromEnum(TeaBuildingType.values(), TeaBuildingType::getValue));
        map.put(INPUT_NAME_AWAITING, createMapFromEnum(TeaName.values(), TeaName::getValue));
        map.put(TYPE_SELECTION_AWAITING, createMapFromEnum(Type.values(), Type::getType));
        map.put(COLOR_SELECTION_AWAITING, createMapFromEnum(Color.values(), Color::getColor));
        map.put(ADDITIONS_AWAITING, createMapFromEnum(Additive.values(), Additive::getAdditive));
        map.put(CUP_BUILDING_TYPE_PROPOSAL, createMapFromEnum(CupBuildingType.values(), CupBuildingType::getType));
        map.put(CUP_NAME_AWAITING, createMapFromEnum(Name.values(), Name::getName));
        map.put(CUP_SIZE_AWAITING, createMapFromEnum(Size.values(), Size::getSize));
        map.put(DELICACY_TYPE_AWAITING, createMapFromEnum(DelicacyType.values(), DelicacyType::getType));
        map.put(DELICACY_COUNT_AWAITING, createMapFromEnum(
                Arrays.stream(DelicacyCount.values())
                        .filter(c -> c != DelicacyCount.ZERO)
                        .toArray(DelicacyCount[]::new),
                e -> String.valueOf(e.getValue())));

        return map;
    }

    private static <T extends OrderAttribute> Map<String, ? extends OrderAttribute> createMapFromEnum(
            T[] values, Function<T, String> getter
    ) {
        return Arrays.stream(values)
                .collect(Collectors.toMap(getter, e -> e));
    }

    public static Optional<? extends OrderAttribute> resolve(OrderState state, String attribute) {
        return Optional.ofNullable(map.get(state))
                .map(m -> m.get(attribute));
    }
}
