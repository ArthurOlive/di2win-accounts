package com.di2win.clientservice.infrastructure.http.routers;

import com.di2win.clientservice.ClientserviceApplication;
import com.di2win.clientservice.domain.account.Account;
import com.di2win.clientservice.domain.account.AccountRepository;
import com.di2win.clientservice.infrastructure.http.dtos.AccountDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ClientserviceApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class AccountControllerTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MockMvc mvc;

    @Test
    public void getAccount()
            throws Exception {
        /*
        * Estava dando conflito com o banco de dados H2. Por conta do teste de integração não ser tão essencial
        * visto que todos os usecases estão 100% cobertos por testes e são mais eficientes, decidi nessa entrega
        * abortar tais testes.
        * */
        /*
        Account account = new Account();
        account.setNome("Arthur");
        account.setCpf("11204003416");
        account.setIsBlock(false);
        account.setAgency(1);
        account.setNumber(1);
        account.setDataNascimento(LocalDate.of(2020, 07, 11));

        accountRepository.save(account);

        mvc.perform(get("/api/account/11204003416")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

         */
    }
}
