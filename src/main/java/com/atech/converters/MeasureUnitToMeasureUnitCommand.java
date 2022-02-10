package com.atech.converters;

import com.atech.commands.MeasureUnitCommand;
import com.atech.entity.MeasureUnit;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MeasureUnitToMeasureUnitCommand implements Converter<MeasureUnit, MeasureUnitCommand> {

    @Synchronized
    @Nullable
    @Override
    public MeasureUnitCommand convert(MeasureUnit source) {

        if (source == null) {
            return null;
        }

        final MeasureUnitCommand measureUnitCommand = new MeasureUnitCommand();
        measureUnitCommand.setId(measureUnitCommand.getId());
        measureUnitCommand.setDescription(source.getUnitOfMeasure());
        return measureUnitCommand;
    }
}
