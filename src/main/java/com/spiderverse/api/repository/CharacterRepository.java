package com.spiderverse.api.repository;

import com.spiderverse.api.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    List<Character> findAllByOrderByNameAsc();

    List<Character> findAllByOrderByCreatedAtAsc();
}
