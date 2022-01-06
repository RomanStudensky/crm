package ru.pnzgu.crm.store.states.converter;

import ru.pnzgu.crm.store.states.LeadState;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class LeadStateConverter implements AttributeConverter<LeadState, String> {

    @Override
    public String convertToDatabaseColumn(LeadState leadState) {
        if (leadState == null) {
            return null;
        }
        return leadState.getLabel();
    }

    @Override
    public LeadState convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(LeadState.values())
                .filter(c -> c.getLabel().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}