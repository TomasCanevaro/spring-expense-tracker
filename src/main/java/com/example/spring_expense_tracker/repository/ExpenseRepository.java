package com.example.spring_expense_tracker.repository;

import com.example.spring_expense_tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByCategory(String category);
    List<Expense> findByDateBetween(LocalDate start, LocalDate end);
}
