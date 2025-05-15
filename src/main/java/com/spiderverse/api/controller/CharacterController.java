package com.spiderverse.api.controller;


import com.spiderverse.api.model.Character;
import com.spiderverse.api.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Character>> getAll(@RequestParam(defaultValue = "createdAt") String orderBy){
        return ResponseEntity.ok(service.getAll(orderBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Character> create(@RequestBody Character character) {
        return ResponseEntity.ok(service.create(character));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Character> patch(@PathVariable Long id, @RequestBody Character character) {
        return ResponseEntity.ok(service.patch(id,character));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
