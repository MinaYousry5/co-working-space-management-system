package com.workspace.booking.service;

import com.workspace.booking.dto.Workspace.WorkspaceCreateRequest;
import com.workspace.booking.dto.Workspace.WorkspaceResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkspaceService {
//    Page<WorkspaceResponse> search(WorkspaceSearchRequest request);
//    WorkspaceResponse findById(Long id);
//    WorkspaceAvailabilityResponse availability(Long workspaceId, LocalDateTime start, LocalDateTime end);
//    List<ResourceResponse> resources(Long locationId);
    WorkspaceResponse create(WorkspaceCreateRequest request);
    WorkspaceResponse update(Long id, WorkspaceCreateRequest request);
    WorkspaceResponse getById(Long id);
    List<WorkspaceResponse> getAll();
    List<WorkspaceResponse> getAvailableForBooking(LocalDateTime startDatetime, LocalDateTime endDatetime);
    void delete(Long id);
}
