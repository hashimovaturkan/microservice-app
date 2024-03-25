package com.microservice.accountservice.service;

import com.microservice.accountservice.entity.Account;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
public class AccountService {

    public AccountService(){

    }
    public Account get(String id) {
        return null;
    }


    public Account save(Account accountDto) {
        return null;
    }

    public Account update(String id, Account accountDto) {
        return null;
    }

    public void delete(String id) {

    }

    public Account findAll(Pageable pageable) {
        return null;
    }
}
