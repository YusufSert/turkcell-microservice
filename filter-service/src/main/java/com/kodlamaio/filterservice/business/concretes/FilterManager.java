package com.kodlamaio.filterservice.business.concretes;

import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.filterservice.business.abstracts.FilterService;
import com.kodlamaio.filterservice.business.dto.reponses.GetAllFiltersResponse;
import com.kodlamaio.filterservice.business.dto.reponses.GetFilterResponse;
import com.kodlamaio.filterservice.entities.Filter;
import com.kodlamaio.filterservice.repository.FilterRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class FilterManager implements FilterService {
    private final FilterRepository repository;
    private final ModelMapperService mapper;

    @Override
    public List<GetAllFiltersResponse> getAll() {
        return repository.findAll()
            .stream()
            .map(filter -> mapper.forResponse().map(filter, GetAllFiltersResponse.class))
            .toList();
    }

    @Override
    public GetFilterResponse getById(String id) {
        Filter filter = repository.findById(id).orElseThrow();
        return mapper.forResponse().map(filter, GetFilterResponse.class);
    }

    @Override
    public void add(Filter filter) {
        repository.save(filter);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByCarId(UUID carId) {
        repository.deleteByCarId(carId);
    }

    @Override
    public void deleteAllByBrandId(UUID brandId) {
        repository.deleteAllByBrandId(brandId);
    }

    @Override
    public void deleteAllByModelId(UUID modelId) {
        repository.deleteAllByModelId(modelId);
    }

    @Override
    public Filter getByCarId(UUID carId) {
        return repository.findByCarId(carId).orElseThrow();
    }
}
