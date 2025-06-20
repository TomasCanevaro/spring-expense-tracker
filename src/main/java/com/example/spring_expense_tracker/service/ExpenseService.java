package com.example.spring_expense_tracker.service;

import com.example.spring_expense_tracker.model.Expense;
import com.example.spring_expense_tracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository repository;

    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    public Optional<Expense> getExpenseById(Long id) {
        return repository.findById(id);
    }

    public Expense addExpense(Expense expense) {
        return repository.save(expense);
    }

    public Optional<Expense> updateExpense(Long id, Expense updated) {
        return repository.findById(id).map(expense -> {
            expense.setTitle(updated.getTitle());
            expense.setCategory(updated.getCategory());
            expense.setAmount(updated.getAmount());
            expense.setDate(updated.getDate());
            return repository.save(expense);
        });
    }

    public void deleteExpense(Long id) {
        repository.deleteById(id);
    }

    public List<Expense> getByCategory(String category) {
        return repository.findByCategory(category);
    }

    public Map<String, BigDecimal> getMonthlySummary(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Expense> expenses = repository.findByDateBetween(start, end);

        return expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)
                ));
    }
}
