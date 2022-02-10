package com.atech.service;

import com.atech.commands.MeasureUnitCommand;
import com.atech.converters.MeasureUnitToMeasureUnitCommand;
import com.atech.repositories.MeasureUnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final MeasureUnitRepository measureUnitRepository;
    private final MeasureUnitToMeasureUnitCommand measureUnitToMeasureUnitCommand;

    public UnitOfMeasureServiceImpl(MeasureUnitRepository measureUnitRepository,
                                    MeasureUnitToMeasureUnitCommand measureUnitToMeasureUnitCommand) {
        this.measureUnitRepository = measureUnitRepository;
        this.measureUnitToMeasureUnitCommand = measureUnitToMeasureUnitCommand;
    }

    @Override
    public List<MeasureUnitCommand> listAllUoms() {
        return StreamSupport.stream(measureUnitRepository.findAll().spliterator(), false)
                .map(measureUnitToMeasureUnitCommand::convert)
                .collect(Collectors.toList());
    }
}
