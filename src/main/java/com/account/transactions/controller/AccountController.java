package com.account.transactions.controller;

import com.account.transactions.model.Account;
import com.account.transactions.model.AccountTransaction;
import com.account.transactions.repository.AccountRepository;
import com.account.transactions.repository.AccountTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    /**
     * Get All Account Items. Deep Copied object of Account returned without AccountTransaction nested object.
     *
     * @param pageable pageable object
     * @return Page with Account result
     */
    @GetMapping("/accounts")
    public Page<Account> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable).map(account -> {
            final Account accountBuilder = Account.builder()
                    .id(account.getId())
                    .accountName(account.getAccountName())
                    .accountNumber(account.getAccountNumber())
                    .accountType(account.getAccountType())
                    .balanceDate(account.getBalanceDate())
                    .currencyType(account.getCurrencyType())
                    .openingBalance(BigDecimal.ZERO)
                    .build();

            // calculate opening balance
            if (!account.getAccountTransactions().isEmpty()) {
                account.getAccountTransactions().forEach(accountTransaction -> {
                    accountBuilder.setOpeningBalance(accountBuilder.getOpeningBalance().add(accountTransaction.getCreditAmount().multiply(BigDecimal.ONE.negate()).add(accountTransaction.getDebitAmount())));
                });
            }

            return accountBuilder;
        });
    }

    /**
     * Find Account items by the defined id and return the AccountTransaction objects related to it as well.
     *
     * @param accountId accountId to search for.
     * @return All Account objects with the category defined as the supplied request parameter
     */
    @GetMapping("/accounts/{accountId}/transactions")
    public Account findByAccountId(@PathVariable Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        account.orElseThrow(() -> new ResourceNotFoundException("Id " + accountId + " not found"));

        return account.get();
    }

    /**
     * POST method added to facilitate adding AccountTransaction objects to an Account. For TESTING ONLY
     *
     * @param accountId          accountId for the Account object against which the AccountTransaction needs to be saved.
     * @param accountTransaction AccountTransaction object to save
     * @return persisted Object
     */
    @PostMapping("/accounts/{accountId}/transactions")
    public AccountTransaction createAccountTransaction(@PathVariable(value = "accountId") Long accountId,
                                                       @Valid @RequestBody AccountTransaction accountTransaction) {
        return accountRepository.findById(accountId).map(account -> {
            accountTransaction.setAccount(account);
            return accountTransactionRepository.save(accountTransaction);
        }).orElseThrow(() -> new ResourceNotFoundException("AccountId " + accountId + " not found"));
    }

    /**
     * POST method added to facilitate adding Account objects. For TESTING ONLY
     *
     * @param post posted object containing the Account data
     * @return the serialized object
     */
    @PostMapping("/accounts")
    public Account createAccount(@Valid @RequestBody Account post) {
        return accountRepository.save(post);
    }
}