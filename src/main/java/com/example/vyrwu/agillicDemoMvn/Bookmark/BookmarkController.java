package com.example.vyrwu.agillicDemoMvn.Bookmark;


import com.example.vyrwu.agillicDemoMvn.Account.Account;
import com.example.vyrwu.agillicDemoMvn.Account.AccountService;
import com.example.vyrwu.agillicDemoMvn.JsonRequests.JsonIDRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/bookmarks")
class BookmarkController {

    @Autowired
    AccountService accountService;

    @Autowired
    BookmarkService bookmarkService;

    // GET for retreiving all bookmarks
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    ResponseEntity getAll() {
        List bookmarks = new ArrayList<Bookmark>();
        accountService.getAll().forEach(
                account -> bookmarks.addAll(account.getBookmarks()));

        return new ResponseEntity<>(bookmarks, HttpStatus.OK);
    }

    // DELETE for deleting bookmarks (generic)
    @RequestMapping(method = RequestMethod.DELETE, produces = "application/json")
    ResponseEntity remove(@RequestBody Bookmark bookmark) {
        bookmarkService.remove(bookmark.getId());

        return ResponseEntity.noContent().header("Operation Status", "Success").build();
    }

    // DELETE for deleting bookmarks (input = JSON with ids)
    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    ResponseEntity remove(@RequestBody JsonIDRequest idList) {
        Arrays.stream(idList.ids).forEach(
                id -> bookmarkService.remove(id));

        return ResponseEntity.noContent().header("Operation Status", "Success").build();
    }

    // GET for getting specific account bookmarks
    @RequestMapping(method = RequestMethod.GET, value = "/{username}", produces = "application/json")
    ResponseEntity get(@PathVariable String username) {
        Collection<Bookmark> bookmarks = bookmarkService.getAll(username);

        return ResponseEntity.ok(bookmarks);
    }

    // POST for adding bookmark to account
    @RequestMapping(method = RequestMethod.POST, value = "/{username}", consumes = "application/json", produces = "application/json")
    ResponseEntity add(@PathVariable String username, @RequestBody Bookmark bookmark) {
        Account account = accountService.get(username);
        bookmarkService.save(
                new Bookmark(account, bookmark.getUri(), bookmark.getDescription()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(account.getUsername()).toUri();

        return ResponseEntity.created(location).build();
    }

    // PUT for updating bookmarks
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    ResponseEntity updateBookmark(@RequestBody Bookmark bookmark) {
        if (!bookmarkService.contains(bookmark.getId())) {
            if (bookmark.getAccount() == null) {
                return ResponseEntity.notFound().build();
            }
        }

        if (bookmarkService.contains(bookmark.getId())) {
            Bookmark bookmarkToUpdate = bookmarkService.get(bookmark.getId());
            bookmarkToUpdate.setUri(bookmark.getUri());
            bookmarkToUpdate.setDescription(bookmark.getDescription());
            bookmarkService.save(bookmarkToUpdate);
        } else {
            add(bookmark.getAccount().getUsername(), bookmark);
        }
        return ResponseEntity.ok().build();
    }
}
