package com.example.vyrwu.agillicDemoMvn.Bookmark;

import java.util.Collection;

// Performs bookmark manipulation operations on specified User Account.
public interface BookmarkService {

    Collection<Bookmark> getAll(String username);

    Bookmark get(Long bookmarkId);

    Bookmark save(Bookmark bookmark);

    void remove(Long bookmarkId);

    void clear();

    boolean contains(Long id);
}
