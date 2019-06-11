package com.example.vyrwu.agillicDemoMvn.Account;

import com.example.vyrwu.agillicDemoMvn.Bookmark.BookmarkService;
import com.example.vyrwu.agillicDemoMvn.JsonRequests.JsonIDRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.security.ProviderException;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

@RestController
@RequestMapping("/users")
class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    BookmarkService bookmarkService;

    // --- GET method for displaying complete account list /users
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    ResponseEntity getAll(HttpServletRequest request) {
        Collection<Account> allAccounts = accountService.getAll();
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.add("Methods", "GET PUT POST DELETE");
        customHeader.add("DELETE", "in:application/json;format:{ids:[long args]}");
        return new ResponseEntity<>(allAccounts, customHeader, HttpStatus.OK);
    }

    // --- OPTIONS for getting http method definitions
    @RequestMapping(method = RequestMethod.OPTIONS, produces = "text/plain")
    ResponseEntity help(HttpServletRequest request) {
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.add("Allows", "GET HEAD PUT POST DELETE OPTIONS");
        return new ResponseEntity<>("DELETE\ninput:application/json\nstructure:{ids:[long args]}", customHeader, HttpStatus.OK);
    }

    // --- POST for adding accounts /users
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    ResponseEntity add(@RequestBody Account account) {
        accountService.save(new Account(account.getUsername(), account.getPassword()));
        Account createdAccount = accountService.get(account.getUsername());
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    // --- DELETE for deleting accounts (also deletes bookmarks of removed account)
    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    ResponseEntity remove(@RequestBody JsonIDRequest idList) {
        Arrays.stream(idList.ids).forEach(id -> {
            if (accountService.contains(id)) {
                accountService.get(id).getBookmarks().forEach(
                        bookmark -> bookmarkService.remove(bookmark.getId()));
                accountService.remove(id);
            } else {
                System.err.println("Missing ID: " + id);
            }
        });
        return ResponseEntity.noContent().build();
    }

    //     --- PUT for updating account credentials /users
    //     If user exists, update credentials. Else, create new user.
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    ResponseEntity put(@RequestBody Account account) {
        if (!accountService.contains(account.getUsername())) {
            return add(account);
        }
        Account targetAccount = accountService.get(account.getUsername());
        targetAccount.setUsername(account.getUsername());
        targetAccount.setPassword(account.getPassword());
        accountService.save(targetAccount);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "exception", method = RequestMethod.GET)
    ResponseEntity ex() throws HttpClientErrorException {
        throw new ProviderException();
    }
}
