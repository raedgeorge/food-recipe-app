package com.atech.repositories;

import com.atech.entity.MeasureUnit;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MeasureUnitRepository extends CrudRepository<MeasureUnit, Integer> {

    Optional<MeasureUnit> findByUnitOfMeasure(String unitMeasure);
}
