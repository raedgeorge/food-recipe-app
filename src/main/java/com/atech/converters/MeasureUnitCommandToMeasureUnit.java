package com.atech.converters;

import com.atech.commands.MeasureUnitCommand;
import com.atech.entity.MeasureUnit;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MeasureUnitCommandToMeasureUnit implements Converter<MeasureUnitCommand, MeasureUnit > {

    @Synchronized
    @Nullable
    @Override
    public MeasureUnit convert(MeasureUnitCommand source) {

        if(source == null) {
            return null;
        }

        final MeasureUnit measureUnit = new MeasureUnit();
        measureUnit.setId(source.getId());
        measureUnit.setUnitOfMeasure(source.getUnitOfMeasure());
        return measureUnit;
    }
}
