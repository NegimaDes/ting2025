package com.example.demo.controller;

import com.example.demo.model.ToolEntity;
import com.example.demo.service.ToolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tools")
public class ToolController {

    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @PostMapping
    public ToolEntity registerTool(@RequestBody ToolEntity tool) {
        return toolService.registerTool(tool);
    }

    @PutMapping("/{id}/deactivate")
    public void deactivateTool(@PathVariable Long id, @RequestParam String role) {
        toolService.deactivateTool(id, role);
    }

    @GetMapping
    public List<ToolEntity> getAllTools() {
        return toolService.listAllTools();
    }
}
