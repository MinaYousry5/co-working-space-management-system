package com.workspace.booking.controller;

import com.workspace.booking.common.ApiResponse;
import com.workspace.booking.dto.workspacetype.WorkspaceTypeRequest;
import com.workspace.booking.dto.workspacetype.WorkspaceTypeResponse;
import com.workspace.booking.service.WorkspaceTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workspace-types")
@RequiredArgsConstructor
public class WorkspaceTypeController {

    private final WorkspaceTypeService service;

    @PostMapping
    public ResponseEntity<ApiResponse<WorkspaceTypeResponse>> create(@Valid @RequestBody WorkspaceTypeRequest request) {
        WorkspaceTypeResponse data = service.create(request);
        return new ResponseEntity<>(ApiResponse.success(data), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<WorkspaceTypeResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(service.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkspaceTypeResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(service.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkspaceTypeResponse>> update(@PathVariable Long id, @Valid @RequestBody WorkspaceTypeRequest request) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted successfully"));
    }
}