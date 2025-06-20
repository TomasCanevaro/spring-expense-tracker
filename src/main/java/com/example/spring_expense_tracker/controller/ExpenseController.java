package com.example.spring_expense_tracker.controller;

import com.example.spring_expense_tracker.model.Expense;
import com.example.spring_expense_tracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService service;

    @GetMapping
    public List<Expense> getAll() {
        return service.getAllExpenses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getById(@PathVariable Long id) {
        return service.getExpenseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Expense add(@RequestBody Expense expense) {
        return service.addExpense(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        return service.updateExpense(id, updatedExpense)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteExpense(id);
    }

    @GetMapping("/category")
    public List<Expense> getByCategory(@RequestParam String category) {
        return service.getByCategory(category);
    }

    @GetMapping("/summary")
    public Map<String, BigDecimal> getMonthlySummary(
            @RequestParam int year,
            @RequestParam int month
    ) {
        return service.getMonthlySummary(year, month);
    }
}