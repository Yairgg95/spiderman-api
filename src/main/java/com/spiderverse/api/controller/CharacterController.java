package com.spiderverse.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spiderverse.api.model.Character;
import com.spiderverse.api.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/characters")
@CrossOrigin(origins = "*")
public class CharacterController {
    private final CharacterService service;


    public CharacterController(CharacterService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Character>> getAll(@RequestParam(defaultValue = "createdAt") String orderBy) {
        return ResponseEntity.ok(service.getAll(orderBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Character> create(
            @RequestPart("character") String characterJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Character character = mapper.readValue(characterJson, Character.class);

        return ResponseEntity.ok(service.create(character, image));
    }

    @PatchMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Character> patch(
            @PathVariable Long id,
            @RequestPart(value = "character", required = false) String characterJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Character character = mapper.readValue(characterJson, Character.class);

        return ResponseEntity.ok(service.patch(id, character, image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
