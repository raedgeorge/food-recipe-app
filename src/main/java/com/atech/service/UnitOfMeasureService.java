package com.atech.service;

import com.atech.commands.MeasureUnitCommand;
import com.atech.entity.MeasureUnit;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {

    Flux<MeasureUnitCommand> listAllUoms();

    void save(MeasureUnit measureUnit);
}
