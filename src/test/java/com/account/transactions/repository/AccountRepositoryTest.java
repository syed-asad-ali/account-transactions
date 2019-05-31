package com.account.transactions.repository;

import com.account.transactions.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(entityManager).isNotNull();
        assertThat(accountRepository).isNotNull();
    }


    @Test
    public void testFindById() {
        Optional<Account> account = accountRepository.findById(Long.parseLong("1"));
        assertThat(account).isNotNull();
    }
}
