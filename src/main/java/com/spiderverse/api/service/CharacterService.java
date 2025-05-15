package com.spiderverse.api.service;


import com.spiderverse.api.model.Character;
import com.spiderverse.api.repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {


    private final CharacterRepository repository;

    public CharacterService(CharacterRepository repository) {
        this.repository = repository;
    }

    public List<Character> getAll(String orderBy) {
        return "name".equals(orderBy)
                ? repository.findAllByOrderByNameAsc()
                : repository.findAllByOrderByCreatedAtAsc();
    }

    public Optional<Character> getById(Long id){
        return repository.findById(id);
    }

    public Character create(Character character){
        return repository.save(character);
    }

    public Character update(Long id, Character updatedCharacter){
        return  repository.findById(id)
                .map(c -> {
                    c.setName(updatedCharacter.getName());
                    c.setIdentifier(updatedCharacter.getIdentifier());
                    c.setImageURL(updatedCharacter.getImageURL());
                    c.setRole(updatedCharacter.getDescription());
                    c.setDescription(updatedCharacter.getDescription());
                    return repository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Character not found"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
