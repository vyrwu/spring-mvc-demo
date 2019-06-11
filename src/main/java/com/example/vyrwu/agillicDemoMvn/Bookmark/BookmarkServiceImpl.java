package com.example.vyrwu.agillicDemoMvn.Bookmark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

// Implementation of BookmarkManager interface.
@Service
class BookmarkServiceImpl implements BookmarkService {

    private BookmarkRepository bookmarkRepository;

    @Autowired // = Setter injection will be autowired by Spring's dependency injection facilities.
    void setBookmarkRepository(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public Collection<Bookmark> getAll(String userId) {
        return bookmarkRepository.findByAccountUsername(userId);
    }

    @Override
    public Bookmark get(Long bookmarkId) {
        return bookmarkRepository.findOne(bookmarkId);
    }

    @Override
    public void remove(Long id) {
        bookmarkRepository.delete(id);
    }

    @Override
    public Bookmark save(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    @Override
    public void clear() {
        bookmarkRepository.deleteAllInBatch();
    }

    @Override
    public boolean contains(Long id) {
        return bookmarkRepository.findById(id).isPresent();
    }

    // !!! Uses Optional as method parameter. Might cause NullPointerException. Old way
//    @Override
//    public Bookmark addBookmark(Optional<Account> accountOptional, Bookmark input) {
//        Bookmark result = new Bookmark(accountOptional.get(), input.uri, input.description);
//        return accountOptional
//                .map(account -> bookmarkRepository.save(result)).get();
//    }

}
