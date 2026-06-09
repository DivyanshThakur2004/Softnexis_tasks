
package com.productivitypro.service;
import java.util.List;
import com.productivitypro.dto.*;
public interface TaskService{
 List<TaskResponse> getAll();
 TaskResponse getById(Long id);
 TaskResponse create(TaskRequest request);
 TaskResponse update(Long id,TaskRequest request);
 void delete(Long id);
}
