package com.pfcti.springbeans;

import com.pfcti.springbeans.dto.ClientQueryDto;
import com.pfcti.springbeans.dto.ClientQueryType;
import com.pfcti.springdata.dto.ClientDto;
import com.pfcti.springdata.repository.ClientRepository;
import com.pfcti.springdata.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ClientAdministratorTest {
    @Autowired
    private ClientAdministrator clientAdministrator;


    @Test
    void getClientsByDni() {
        ClientQueryDto clientQueryDto = new ClientQueryDto();

        clientQueryDto.setTextFilter("1");

        List<ClientDto> clientDtos = clientAdministrator.getClientsByCriteria(clientQueryDto);
        assertEquals(1, clientDtos.size());
    }

    @Test
    void getClientsByName() {
        ClientQueryDto clientQueryDto = new ClientQueryDto();

        clientQueryDto.setTextFilter("ROBERTO");

        List<ClientDto> clientDtos = clientAdministrator.getClientsByCriteria(clientQueryDto);
        assertEquals(1, clientDtos.size());
    }
}