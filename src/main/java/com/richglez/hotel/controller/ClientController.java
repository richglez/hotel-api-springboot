package com.richglez.hotel.controller;

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
    public List<Client> getClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id){
        return clientService.getClientById(id);
    }

    @PostMapping
    public Client addClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }


}
