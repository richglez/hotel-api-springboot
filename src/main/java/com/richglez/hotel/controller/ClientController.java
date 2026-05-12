package com.richglez.hotel.controller;

import com.richglez.hotel.dto.ClientPatchRequest;
import com.richglez.hotel.dto.ClientResponse;
import com.richglez.hotel.model.Client;
import com.richglez.hotel.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    // Inyeccion de dependencias automatica
    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<ClientResponse> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ClientResponse getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @PostMapping
    public ClientResponse createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @PutMapping("/{id}")
    public ClientResponse updateClient(@PathVariable Long id, @RequestBody Client clientData) {
        return clientService.updateClient(id, clientData);
    }

    @PatchMapping("/{id}")
    public ClientResponse patchClient(@PathVariable Long id, @RequestBody ClientPatchRequest request) {
        return clientService.patchClient(id, request);
    }

    @DeleteMapping("/{id}")
    public ClientResponse softDeleteClient(@PathVariable Long id) {
        return clientService.softDeleteClientById(id);
    }

    @DeleteMapping("/{id}/permanent")
    public ClientResponse hardDeleteClient(@PathVariable Long id) {
        return clientService.hardDeleteClientById(id);
    }


}
