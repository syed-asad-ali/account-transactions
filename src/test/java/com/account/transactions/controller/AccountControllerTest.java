package com.account.transactions.controller;

import com.account.transactions.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountController controller;

    @MockBean
    private Pageable pageable;


    @Test
    public void givenAccounts_whenGetAllAccounts_thenReturnJsonArray()
            throws Exception {

        Account account = Account.builder().id(Long.parseLong("1")).accountNumber("test").accountName("Test").build();
        Page<Account> accountPage = new PageImpl<>(Collections.singletonList(account));
        given(controller.getAllAccounts(pageable)).willReturn(accountPage);

        mvc.perform(get("/accounts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
