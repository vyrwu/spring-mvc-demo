package com.example.vyrwu.agillicDemoMvn.Account;

import java.util.Collection;
import java.util.Optional;

// Interface managing basic Account functions
public interface AccountService {

    void validateUser(String username); // Unused

    Account save(Account account);

    Account get(String username);

    Account get(Long id);

    Collection<Account> getAll();

    void remove(Long id);

    boolean contains(String username);

    boolean contains(Long id);

    void clear();
}
