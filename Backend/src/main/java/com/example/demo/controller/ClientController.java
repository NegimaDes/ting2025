package com.example.demo.controller;

import com.example.demo.model.ClientEntity;
import com.example.demo.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ClientEntity registerClient(@RequestBody ClientEntity client) {
        return clientService.registerClient(client);
    }

    @PutMapping("/{id}/restrict")
    public void restrictClient(@PathVariable Long id) {
        clientService.restrictClient(id);
    }

    @PutMapping("/{id}/activate")
    public void activateClient(@PathVariable Long id) {
        clientService.activateClient(id);
    }

    @GetMapping
    public List<ClientEntity> listClients() {
        return clientService.listAllClients();
    }
}
