package ru.pnzgu.crm.store.states.converter;

import ru.pnzgu.crm.store.states.ActivityState;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ActivityStateConverter implements AttributeConverter<ActivityState, String> {

    @Override
    public String convertToDatabaseColumn(ActivityState activityState) {
        if (activityState == null) {
            return null;
        }
        return activityState.getLabel();
    }

    @Override
    public ActivityState convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(ActivityState.values())
                .filter(c -> c.getLabel().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}