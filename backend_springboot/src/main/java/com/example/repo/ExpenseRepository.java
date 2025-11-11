package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
