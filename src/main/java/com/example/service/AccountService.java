package com.example.service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }

    /**
     * createAccount()
     * Given a brand new transient account,
     * persist the account to the database (create a new database record for the account entity.)
     * @param account
     * @return persisted Account entity
     */
    public Account createAccount(Account account){
        return accountRepository.save(account);
        // .save method comes from JpaRepository that we included in the Repository layer, 
        // which provides built-in database operations like .save(), .findAll() and etc
    }

    /**
     * getAccountByUsername
     * This method uses the custom spring query findByUsername 
     * which is not one of Jpa repository's default methods
     * @param account
     * @return account entity with the given username
     */
    public Account getAccountByUsername(Account account){
        return accountRepository.findByUsername(account.getUsername());
    }

    /**
     * getAccountByUsername
     * This method uses the custom spring query findByUsername 
     * which is not one of Jpa repository's default methods
     * Checks whether the password associated with the username matches the given account password
     * An account with the given username does not exist return null
     * An account with the given password does not exist return null
     * 
     * @param account
     * @return account entity with the given username and password
     */
    public Account getAccountByUsernameandPassword(Account account){
        if(accountRepository.findByUsername(account.getUsername())==null){
            return null;
        }else if(!accountRepository.findByUsername(account.getUsername()).getPassword().equals(account.getPassword())){
            //when comparing strings we cannot use ==. Becuase == compares Object references and not the literal values
            //which is why when comparing Strings we should use .equals() method
            return null;
        }
        return accountRepository.findByUsername(account.getUsername());
    }

    
}
