package com.example.zboruri2.service;

import com.example.zboruri2.model.Client;
import com.example.zboruri2.repository.ClientDBRepository;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private ClientDBRepository clientDBRepository;

    public ClientService(ClientDBRepository clientDBRepository) {
        this.clientDBRepository = clientDBRepository;
    }

    public List<Client> findAll() {
        return clientDBRepository.findAll();
    }

    public Optional<Client> findOneByUsername(String usernameInput) {
        return clientDBRepository.findOneByUsername(usernameInput);
    }
}
