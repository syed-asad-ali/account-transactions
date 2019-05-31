package com.account.transactions.repository;

import com.account.transactions.model.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
}