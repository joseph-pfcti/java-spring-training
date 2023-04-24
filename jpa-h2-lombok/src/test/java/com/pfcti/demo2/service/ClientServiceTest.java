package com.pfcti.demo2.service;

import com.pfcti.demo2.dto.ClientDto;
import com.pfcti.demo2.model.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.BeforeEach.*;

@SpringBootTest
@Slf4j
class ClientServiceTest {

    @Autowired
    private ClientService clientService;
    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    public void insertClient () {
        ClientDto clientDto = new ClientDto();
        clientDto.setName("Joseph");
        clientDto.setLastNames("Ramirez Marchena");
        clientDto.setDni("1111111");
        clientDto.setPhone("88888888");
        clientDto.setCountry("CR");

//        clientService.insert(clientDto);
    }

    @Test
    void insert() {
        List<Client> clients = entityManager.createQuery("SELECT c FROM Client c").getResultList();
        System.out.println("List before insert: " + clients.size());

        ClientDto clientDto = new ClientDto();
        clientDto.setName("Joseph");
        clientDto.setLastNames("Ramirez Marchena");
        clientDto.setDni("1111111");
        clientDto.setPhone("88888888");

        clientService.insert(clientDto);

        clients = entityManager.createQuery("SELECT c FROM Client c").getResultList();
        assertFalse(clients.isEmpty());
        System.out.println("lista después de insertar:" + clients.size());
        assertEquals("1", clients.get(0).getDni());
    }

    @Test
    void find () {
        Client client = clientService.find(1);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        clientDto.setLastNames(client.getLastNames());
        clientDto.setPhone(client.getPhone());

        System.out.println("Client: " + clientDto.toString());

        assertEquals(1, clientDto.getId());
    }

    @Test
    void update () {
        Client client = clientService.find(1);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName() + " TESTING");

        clientService.update(clientDto);

        List<Client> clients = entityManager.createQuery("SELECT c FROM Client c").getResultList();
        System.out.println("Current clients size: " + clients.get(0).getName());
        assertEquals("Joseph TESTING", clients.get(0).getName());
    }

    @Test
    void delete () {
        clientService.delete(1);
        List<Client> clients = entityManager.createQuery("SELECT c FROM Client c").getResultList();
        System.out.println("Current clients size: " + clients.size());
        assertTrue(clients.size() > 1);
    }

    @Test
    void getClientsByCountryAndActiveAccounts() {
        String ISOCountryCode = "CR";
        List<ClientDto> clients = clientService.getClientsByCountryAndActiveAccounts(ISOCountryCode);

        assertTrue(clients.size() >= 1, "The client size was: " + clients.size());
    }

    @Test
    void getClientsByCountryWhereCountryIsNotAndInactiveCard() {
        String ISOCountryCode = "CR";
        List<ClientDto> clients = clientService.findClientByCountryAndCardsStateIsFalse(ISOCountryCode);

        assertTrue(clients.size() >= 1, "The client size was: " + clients.size());
    }

    @Test
    void findClientByLastNames () {
        List<Client> clients = clientService.findClientByLastNames("Ramirez Marchena");

        assertTrue(clients.size() > 1, "Clients size was: " + clients.size());
    }

    @Test
    void findClientByLastNamesNative () {
        List<ClientDto> clients = clientService.findClientByLastNamesNative("Ramirez Marchena");

        assertTrue(clients.size() > 1, "Clients size was: " + clients.size());
    }
}