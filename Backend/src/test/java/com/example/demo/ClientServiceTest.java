package com.example.demo;

import com.example.demo.model.ClientEntity;
import com.example.demo.model.ClientStatus;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {

    private final ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
    private final ClientService clientService = new ClientService(clientRepository);

    @Test
    void registerClient_shouldSetStatusActive() {
        ClientEntity client = ClientEntity.builder()
                .name("John Doe")
                .rut("12345678-9")
                .phone("555-1234")
                .email("john@example.com")
                .build();

        Mockito.when(clientRepository.save(Mockito.any(ClientEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ClientEntity saved = clientService.registerClient(client);

        assertEquals(ClientStatus.ACTIVE, saved.getStatus());
    }

    @Test
    void restrictClient_shouldChangeStatusToRestricted() {
        ClientEntity client = ClientEntity.builder().id(1L).status(ClientStatus.ACTIVE).build();
        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        clientService.restrictClient(1L);

        assertEquals(ClientStatus.RESTRICTED, client.getStatus());
    }
}
