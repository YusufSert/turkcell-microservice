package com.kodlamaio.maintenanceservice.api.controllers;

import com.kodlamaio.maintenanceservice.business.abstracts.MaintenanceService;
import com.kodlamaio.maintenanceservice.business.dto.reponses.CreateMaintenanceResponse;
import com.kodlamaio.maintenanceservice.business.dto.reponses.GetAllMaintenancesResponse;
import com.kodlamaio.maintenanceservice.business.dto.reponses.GetMaintenanceResponse;
import com.kodlamaio.maintenanceservice.business.dto.reponses.UpdateMaintenanceResponse;
import com.kodlamaio.maintenanceservice.business.dto.requests.CreateMaintenanceRequest;
import com.kodlamaio.maintenanceservice.business.dto.requests.UpdateMaintenanceRequest;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/maintenance")
public class MaintenancesController {
    private final MaintenanceService service;

    @GetMapping
    public List<GetAllMaintenancesResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetMaintenanceResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public CreateMaintenanceResponse add(@Valid @RequestBody CreateMaintenanceRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateMaintenanceResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateMaintenanceRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.getById(id);
    }
}

