package com.richglez.hotel.service;

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
        client.setPhone(newClient.getPhone());

        return clientRepository.save(client);
    }

    

}
