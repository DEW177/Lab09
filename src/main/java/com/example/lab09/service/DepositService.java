package com.example.lab09.service;

import com.example.lab09.model.Account;
import com.example.lab09.model.DepositTransaction;
import com.example.lab09.repository.AccountRepository;
import com.example.lab09.repository.DepositRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepositService {

    private final AccountRepository accountRepository;
    private final DepositRepository depositRepository;

    public DepositService(AccountRepository accountRepository,
                          DepositRepository depositRepository) {
        this.accountRepository = accountRepository;
        this.depositRepository = depositRepository;
    }

    @Transactional
    public void deposit(Long accountId, double amount) {

        // 1. ค้นหาบัญชี
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // 2. เพิ่มเงินในบัญชี
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        // 3. บันทึกประวัติการฝากเงิน
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(amount);
        transaction.setAccount(account);

        depositRepository.save(transaction);
    }
}