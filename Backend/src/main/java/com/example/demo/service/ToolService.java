package com.example.demo.service;

import com.example.demo.model.ToolEntity;
import com.example.demo.model.ToolStatus;
import com.example.demo.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToolService {

    private final ToolRepository toolRepository;

    public ToolService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public ToolEntity registerTool(ToolEntity tool) {
        if (tool.getName() == null || tool.getCategory() == null || tool.getReplacementValue() <= 0) {
            throw new IllegalArgumentException("Tool must have name, category, and replacement value.");
        }
        tool.setStatus(ToolStatus.AVAILABLE);
        return toolRepository.save(tool);
    }

    public void deactivateTool(Long id, String role) {
        if (!"ADMIN".equals(role)) {
            throw new SecurityException("Only administrators can deactivate tools.");
        }
        Optional<ToolEntity> toolOpt = toolRepository.findById(id);
        if (toolOpt.isPresent()) {
            ToolEntity tool = toolOpt.get();
            tool.setStatus(ToolStatus.DEACTIVATED);
            toolRepository.save(tool);
        } else  {
            throw new SecurityException("Tool not found");
        }
    }

    public List<ToolEntity> listAllTools() {
        return toolRepository.findAll();
    }
}
