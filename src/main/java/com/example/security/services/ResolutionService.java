package com.example.security.services;

import com.example.security.entities.Resolution;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface ResolutionService {
    List<Resolution> findByOwner(Long owner);

    @Modifying
    @Query("UPDATE Resolution SET text = :text WHERE id = :id")
    Optional<Resolution> revise(Long id, String text, Authentication authentication);

    @Modifying
    @Query("UPDATE Resolution SET completed = 1 WHERE id = :id")
    Optional<Resolution> complete(Long id, Authentication authentication);

    Resolution findById(long id, Authentication authentication);
    Resolution save(String text, Authentication authentication);
}

