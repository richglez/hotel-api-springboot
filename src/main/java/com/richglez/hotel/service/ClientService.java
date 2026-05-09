package com.richglez.hotel.service;

import com.richglez.hotel.dto.ClientPatchRequest;
import com.richglez.hotel.dto.ClientResponse;
import com.richglez.hotel.model.Client;
import com.richglez.hotel.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client with id " + id + " not found"));
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client newClient) {
        Client client = getClientById(id);

        client.setName(newClient.getName());
        client.setEmail(newClient.getEmail());
        client.setPassword(newClient.getPassword());
        client.setPhone(newClient.getPhone());

        return clientRepository.save(client);
    }

    public ClientResponse patchClient(Long id, ClientPatchRequest request) {
        Client client = getClientById(id);

        if (request.getName() != null) client.setName(request.getName());
        if (request.getEmail() != null) client.setEmail(request.getEmail());
        if (request.getPassword() != null) client.setPassword(request.getPassword());
        if (request.getPhone() != null) client.setPhone(request.getPhone());

        Client saved = clientRepository.save(client);
        return toResponse(saved);

    }

    private ClientResponse toResponse(Client client) {
        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setName(client.getName());
        response.setEmail(client.getEmail());
        response.setPhone(client.getPhone());
        response.setCreatedAt(client.getCreatedAt());
        response.setUpdatedAt(client.getUpdatedAt());
        return response;
    }
}