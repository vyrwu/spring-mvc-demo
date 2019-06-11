package com.example.vyrwu.agillicDemoMvn.Account;

import com.example.vyrwu.agillicDemoMvn.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void validateUser(String username) {
        this.accountRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account get(String username) {
        return accountRepository.findByUsername(username).get();
    }

    @Override
    public Account get(Long id) {
        return accountRepository.findOne(id);
    }

    @Override
    public Collection<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public void remove(Long id) {
        accountRepository.delete(id);
    }

    @Override
    public boolean contains(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean contains(Long id) {
        return accountRepository.findById(id).isPresent();
    }

    @Override
    public void clear() {
        accountRepository.deleteAllInBatch();
    }
}
