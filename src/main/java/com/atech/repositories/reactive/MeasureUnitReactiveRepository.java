package com.atech.repositories.reactive;

import com.atech.entity.MeasureUnit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface MeasureUnitReactiveRepository extends ReactiveMongoRepository<MeasureUnit, String> {

    Mono<MeasureUnit> findByUnitOfMeasure(String uom);
}
