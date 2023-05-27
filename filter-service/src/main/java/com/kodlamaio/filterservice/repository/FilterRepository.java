package com.kodlamaio.filterservice.repository;

import com.kodlamaio.filterservice.entities.Filter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface FilterRepository extends MongoRepository<Filter, String> {
    public void deleteByCarId(UUID id);
    public void deleteAllByBrandId(UUID id);

    public void deleteAllByModelId(UUID id);

    public Optional<Filter> findByCarId(UUID id);
}
