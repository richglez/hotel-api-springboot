package com.richglez.hotel.service;

import com.richglez.hotel.dto.ClientRequest;
import com.richglez.hotel.dto.ClientResponse;
import com.richglez.hotel.model.Client;
import com.richglez.hotel.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClientResponse createClient(ClientRequest request) {

        Client client = new Client();

        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        client.setPhone(request.getPhone());

        return toResponse(clientRepository.save(client));
    }

    public List<ClientResponse> getAllClients() {
        return clientRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(this::toResponse) // DTO response
                .toList();
    }

    // Metodo privado - para funcionamiento de logica
    private Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client with id " + id + " not found"));

    }

    // Metodo publico
    public ClientResponse getClientById(Long id) {
        return toResponse(findClientById(id)); // DTO response
    }


    public ClientResponse updateClient(Long id, ClientRequest request) {
        Client client = findClientById(id);

        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        client.setPhone(request.getPhone());

        return toResponse(clientRepository.save(client));
    }

    public ClientResponse patchClient(Long id, ClientRequest request) {
        Client client = findClientById(id);

        if (request.getName() != null) client.setName(request.getName());
        if (request.getEmail() != null) client.setEmail(request.getEmail());
        if (request.getPassword() != null) client.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getPhone() != null) client.setPhone(request.getPhone());

        Client saved = clientRepository.save(client);
        return toResponse(saved); // DTO response

    }

    public ClientResponse softDeleteClientById(Long id) {
        Client client = findClientById(id);

        client.setDeletedAt(LocalDateTime.now());
        return toResponse(clientRepository.save(client));
    }

    public ClientResponse hardDeleteClientById(Long id) {
        Client client = findClientById(id);

        clientRepository.delete(client);
        return toResponse(client);
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