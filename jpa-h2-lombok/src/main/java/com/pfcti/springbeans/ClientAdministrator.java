package com.pfcti.springbeans;

import com.pfcti.springbeans.dto.ClientQueryDto;
import com.pfcti.springbeans.dto.ClientQueryType;
import com.pfcti.springdata.dto.ClientDto;
import com.pfcti.springdata.model.Client;
import com.pfcti.springdata.repository.ClientRepository;
import com.pfcti.springdata.service.ClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientAdministrator {
    private ClientRepository clientRepository;
    private ClientService clientService;

    private ClientQueryType clientQueryType;

    public ClientAdministrator (ClientRepository clientRepository, ClientService clientService, ClientQueryType defaultClientQueryType) {
        this.clientRepository = clientRepository;
        this.clientService = clientService;
        this.clientQueryType = defaultClientQueryType;
    }

    public List<ClientDto> getClientsByCriteria(ClientQueryDto clientQueryDto) {
        if (clientQueryDto.getClientQueryType() == null) {
            clientQueryDto.setClientQueryType(this.clientQueryType);
        }

        List<Client> clients = null;
        if (ClientQueryType.DNI.equals(clientQueryDto.getClientQueryType())) {
            clients = this.clientRepository.findByDni(clientQueryDto.getTextFilter());
        } else if (ClientQueryType.NAME.equals(clientQueryDto.getClientQueryType())) {
            clients = this.clientRepository.findByNameContainingIgnoreCaseOrLastNamesContainingIgnoreCase(clientQueryDto.getTextFilter(), clientQueryDto.getTextFilter());
        }

        return Optional
                .ofNullable(clients)
                .map(clientsAux-> clientsAux.stream()
                        .map(client-> this.clientService.fromClientToClientDto(client))
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }

}
