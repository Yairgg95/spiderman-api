package com.spiderverse.api.service;


import com.spiderverse.api.exception.NotFoundException;
import com.spiderverse.api.model.Character;
import com.spiderverse.api.repository.CharacterRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository repository;
    private final S3Service s3Service;

    public CharacterService(CharacterRepository repository, S3Service s3Service) {
        this.repository = repository;
        this.s3Service = s3Service;
    }

    @Cacheable(value = "characters", key = "#orderBy")
    public List<Character> getAll(String orderBy) {
        return "name".equals(orderBy)
                ? repository.findAllByOrderByNameAsc()
                : repository.findAllByOrderByCreatedAtAsc();
    }

    @Cacheable(value = "characterById", key = "#id")
    public Character getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Character with id " + id + " not found"));
    }

    @CacheEvict(value =  {"characters", "characterById"}, allEntries = true)
    public Character create(Character character, MultipartFile image) throws IOException {
        String imageUrl = (image != null && !image.isEmpty())
                ? s3Service.uploadFile(image, character.getIdentifier())
                : "https://spiderverse-images.s3.us-east-2.amazonaws.com/spider-default.avif";
        character.setImageURL(imageUrl);
        return repository.save(character);
    }

    @CacheEvict(value =  {"characters", "characterById"}, allEntries = true)
    public Character patch(Long id, Character updatedCharacter, MultipartFile image) throws IOException {
        Character character = getById(id);

        if (updatedCharacter != null) {
            if (updatedCharacter.getName() != null) character.setName(updatedCharacter.getName());
            if (updatedCharacter.getIdentifier() != null) character.setIdentifier(updatedCharacter.getIdentifier());
            if (updatedCharacter.getRole() != null) character.setRole(updatedCharacter.getRole());
            if (updatedCharacter.getDescription() != null) character.setDescription(updatedCharacter.getDescription());
        }

        if (image != null && !image.isEmpty()) {
            String imageUrl = s3Service.uploadFile(image, character.getIdentifier());
            character.setImageURL(imageUrl);
        }

        return repository.save(character);
    }

    @CacheEvict(value =  {"characters", "characterById"}, allEntries = true)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Character with id " + id + " not found");
        }
        repository.deleteById(id);
    }
}
