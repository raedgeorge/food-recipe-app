package com.atech.service;

import com.atech.commands.MeasureUnitCommand;
import com.atech.entity.MeasureUnit;

import java.util.List;

public interface UnitOfMeasureService {

    List<MeasureUnitCommand> listAllUoms();

    void save(MeasureUnit measureUnit);
}
