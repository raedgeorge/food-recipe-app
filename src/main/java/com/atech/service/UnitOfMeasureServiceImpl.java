package com.atech.service;

import com.atech.commands.MeasureUnitCommand;
import com.atech.converters.MeasureUnitToMeasureUnitCommand;
import com.atech.entity.MeasureUnit;
import com.atech.repositories.reactive.MeasureUnitReactiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final MeasureUnitReactiveRepository measureUnitReactiveRepository;
    private final MeasureUnitToMeasureUnitCommand measureUnitToMeasureUnitCommand;

    public UnitOfMeasureServiceImpl(
            MeasureUnitReactiveRepository measureUnitReactiveRepository,
            MeasureUnitToMeasureUnitCommand measureUnitToMeasureUnitCommand) {

        this.measureUnitReactiveRepository = measureUnitReactiveRepository;
        this.measureUnitToMeasureUnitCommand = measureUnitToMeasureUnitCommand;
    }

    @Override
    @Transactional
    public Flux<MeasureUnitCommand> listAllUoms() {
        return measureUnitReactiveRepository.findAll()
                .map(measureUnitToMeasureUnitCommand::convert);
    }

    @Override
    @Transactional
    public void save(MeasureUnit measureUnit) {

        measureUnitReactiveRepository.save(measureUnit);
    }

}
