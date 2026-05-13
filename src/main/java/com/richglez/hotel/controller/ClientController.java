package com.richglez.hotel.controller;

import com.richglez.hotel.dto.ClientRequest;
import com.richglez.hotel.dto.ClientResponse;
import com.richglez.hotel.model.Client;
import com.richglez.hotel.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(client));
    }

    @PutMapping("/{id}")
    public ClientResponse updateClient(@PathVariable Long id, @Valid @RequestBody Client clientData) {
        return clientService.updateClient(id, clientData);
    }

    @PatchMapping("/{id}")
    public ClientResponse patchClient(@PathVariable Long id, @RequestBody ClientRequest request) {
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
