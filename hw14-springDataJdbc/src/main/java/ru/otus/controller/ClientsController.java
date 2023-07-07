package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.otus.model.Client;
import ru.otus.service.DataJdbcClientsService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientsController {
    private final DataJdbcClientsService dataJdbcClientsService;

    public ClientsController(DataJdbcClientsService dbServiceClient) {
        this.dataJdbcClientsService = dbServiceClient;
    }

    @GetMapping("/client")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = dataJdbcClientsService.findAll();
        return ResponseEntity.ok(clients.stream()
                .map(Client::clone)
                .collect(Collectors.toList()));
    }

    @PostMapping("/client")
    public ResponseEntity<Client> saveClient(@RequestBody Client client) {
        Client created = dataJdbcClientsService.saveClient(client);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }
}