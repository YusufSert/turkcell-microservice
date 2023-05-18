package com.kodlamaio.filterservice.business.abstracts;

import com.kodlamaio.filterservice.business.dto.GetAllFilterResponse;
import com.kodlamaio.filterservice.business.dto.GetFilterResponse;
import com.kodlamaio.filterservice.entities.Filter;

import java.util.List;
import java.util.UUID;

public interface FilterService {
    List<GetAllFilterResponse> getAll();
    GetFilterResponse getById(String id);
    void add(Filter filter);
    void delete(String id);
    void deleteByCarId(UUID carId);
    void deleteAllByBrandId(UUID brandId);
    void deleteAllByModelId(UUID modelId);
    Filter getByCarId(UUID carId);
}
