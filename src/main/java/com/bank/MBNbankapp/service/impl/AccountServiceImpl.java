package com.bank.MBNbankapp.service.impl;

import com.bank.MBNbankapp.dto.AccountDto;
import com.bank.MBNbankapp.entity.Account;
import com.bank.MBNbankapp.mapper.AccountMapper;
import com.bank.MBNbankapp.repository.AccountRepository;
import com.bank.MBNbankapp.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        // Map DTO to Entity
        Account account = AccountMapper.mapToAccount(accountDto);

        // Save Entity in Repository
        Account savedAccount = accountRepository.save(account);

        // Map Entity back to DTO
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
      Account account= accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account not found"));
        return AccountMapper.mapToAccountDto(account);

    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account= accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account not found"));
      double total= account.getBalance()+amount;
      account.setBalance(total);
      Account savedAccount=accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account=accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account not found"));
        if(account.getBalance()<amount){
            throw new RuntimeException("Insufficient balance");
        }
        double total= account.getBalance()-amount;
        account.setBalance(total);
        Account savedAccount=accountRepository.save(account);

         return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        // Fetch all accounts from the repository
        List<Account> accounts = accountRepository.findAll();

        // Map each Account entity to AccountDto and collect as a list
        return accounts.stream()
                .map(AccountMapper::mapToAccountDto) // Using method reference for clarity
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account=accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account not found"));

       accountRepository.deleteById(id);
    }

}
