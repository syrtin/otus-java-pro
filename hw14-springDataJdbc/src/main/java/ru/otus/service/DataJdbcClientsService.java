package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.model.Client;
import ru.otus.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataJdbcClientsService implements ClientService{
    private static final Logger log = LoggerFactory.getLogger(DataJdbcClientsService.class);

    private final ClientRepository clientRepository;

    public DataJdbcClientsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveClient(Client client) {
        log.info("Saved client: {}", client);
        return clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        var clientList = new ArrayList<>(clientRepository.findAll());
        log.info("ClientList:{}", clientList);
        return clientList;
    }
}
