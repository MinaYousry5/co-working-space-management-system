package com.workspace.booking.service;

import com.workspace.booking.dto.workspacetype.WorkspaceTypeRequest;
import com.workspace.booking.dto.workspacetype.WorkspaceTypeResponse;

import java.util.List;

public interface WorkspaceTypeService {
    WorkspaceTypeResponse create(WorkspaceTypeRequest request);
    WorkspaceTypeResponse update(Long id, WorkspaceTypeRequest request);
    WorkspaceTypeResponse getById(Long id);
    List<WorkspaceTypeResponse> getAll();
    void delete(Long id);
}
