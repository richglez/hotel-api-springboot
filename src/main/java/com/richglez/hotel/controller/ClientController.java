package com.richglez.hotel.controller;

import com.richglez.hotel.dto.ClientPatchRequest;
import com.richglez.hotel.dto.ClientResponse;
import com.richglez.hotel.model.Client;
import com.richglez.hotel.service.ClientService;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.createClient(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(@RequestBody Client newClient, @PathVariable Long id) {
        return ResponseEntity.ok(clientService.updateClient(id, newClient));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponse> patchClient(@PathVariable Long id, @RequestBody ClientPatchRequest request) {
        return ResponseEntity.ok(clientService.patchClient(id, request));
    }


}
