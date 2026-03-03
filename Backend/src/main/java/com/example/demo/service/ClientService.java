package com.example.demo.service;

import com.example.demo.model.ClientEntity;
import com.example.demo.model.ClientStatus;
import com.example.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientEntity registerClient(ClientEntity client) {
        if (client.getName() == null || client.getRut() == null ||
                client.getPhone() == null || client.getEmail() == null) {
            throw new IllegalArgumentException("Client must have name, RUT, phone, and email.");
        }
        client.setStatus(ClientStatus.ACTIVE);
        return clientRepository.save(client);
    }

    public void restrictClient(Long id) {
        ClientEntity client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        client.setStatus(ClientStatus.RESTRICTED);
        clientRepository.save(client);
    }

    public void activateClient(Long id) {
        ClientEntity client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        client.setStatus(ClientStatus.ACTIVE);
        clientRepository.save(client);
    }

    public List<ClientEntity> listAllClients() {
        return clientRepository.findAll();
    }

}
