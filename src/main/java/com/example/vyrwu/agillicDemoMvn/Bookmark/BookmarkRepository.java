package com.example.vyrwu.agillicDemoMvn.Bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

// Bookmark JPA repository
@Repository
interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Collection<Bookmark> findByAccountUsername(String username);

    Optional<Bookmark> findById(Long id);
}
