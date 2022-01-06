package ru.pnzgu.crm.store.states.converter;

import ru.pnzgu.crm.store.states.DealState;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class DealStateConverter implements AttributeConverter<DealState, String> {

    @Override
    public String convertToDatabaseColumn(DealState activityState) {
        if (activityState == null) {
            return null;
        }
        return activityState.getLabel();
    }

    @Override
    public DealState convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(DealState.values())
                .filter(c -> c.getLabel().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}