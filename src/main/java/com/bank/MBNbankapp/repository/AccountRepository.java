package com.bank.MBNbankapp.repository;

import com.bank.MBNbankapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}

