package com.example.lab09.controller;

import com.example.lab09.model.Account;
import com.example.lab09.service.AccountService;
import com.example.lab09.service.DepositService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final DepositService depositService;

    public AccountController(AccountService accountService,
                             DepositService depositService) {
        this.accountService = accountService;
        this.depositService = depositService;
    }

    // สร้างบัญชี
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    // ดูข้อมูลบัญชี
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    // ฝากเงิน
    @PostMapping("/{id}/deposit")
    public Map<String, String> deposit(@PathVariable Long id,
                                       @RequestBody DepositRequest request) {

        depositService.deposit(id, request.getAmount());

        // สร้างผลลัพธ์เป็น JSON {"message": "Deposit successful"}
        Map<String, String> response = new HashMap<>();
        response.put("message", "Deposit successful");
        return response;
    }

    // รับข้อมูลจำนวนเงินฝาก
    public static class DepositRequest {

        private double amount;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }
}