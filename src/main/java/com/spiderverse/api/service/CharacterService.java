package com.spiderverse.api.service;


import com.spiderverse.api.exception.NotFoundException;
import com.spiderverse.api.model.Character;
import com.spiderverse.api.repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Character getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Character with id " + id + " not found"));
    }

    public Character create(Character character) {
        return repository.save(character);
    }

    public Character patch(Long id, Character updatedCharacter) {
        return repository.findById(id)
                .map(c -> {
                    if (updatedCharacter.getName() != null) c.setName(updatedCharacter.getName());
                    if (updatedCharacter.getIdentifier() != null) c.setIdentifier(updatedCharacter.getIdentifier());
                    if (updatedCharacter.getImageURL() != null) c.setImageURL(updatedCharacter.getImageURL());
                    if (updatedCharacter.getRole() != null) c.setRole(updatedCharacter.getRole());
                    if (updatedCharacter.getDescription() != null) c.setDescription(updatedCharacter.getDescription());
                    return repository.save(c);
                })
                .orElseThrow(() -> new NotFoundException("Character with id " + id + " not found"));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Character with id " + id + " not found");
        }
        repository.deleteById(id);
    }
}
