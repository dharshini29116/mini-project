package com.example.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.repo.ExpenseRepository;
import com.example.model.Expense;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin("*")
public class ExpenseController {
    @Autowired
    private ExpenseRepository repo;

    @GetMapping
    public List<Expense> all() {
        return repo.findAll();
    }

    @GetMapping("/<built-in function id>")
    public ResponseEntity<Expense> getOne(@PathVariable Long id) {
        Optional<Expense> e = repo.findById(id);
        return e.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Expense create(@RequestBody Expense e) {
        return repo.save(e);
    }

    @PutMapping("/<built-in function id>")
    public ResponseEntity<Expense> update(@PathVariable Long id, @RequestBody Expense in) {
        return repo.findById(id).map(e -> {
            e.setTitle(in.getTitle());
            e.setCategory(in.getCategory());
            e.setAmount(in.getAmount());
            e.setDate(in.getDate());
            repo.save(e);
            return ResponseEntity.ok(e);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/<built-in function id>")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else return ResponseEntity.notFound().build();
    }
}
