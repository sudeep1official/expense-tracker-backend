package com.expensetracker.expense_tracker_api.repository;

import com.expensetracker.expense_tracker_api.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Page<Expense> findByUserId(Long userId, Pageable pageable);
    Optional<Expense> findByIdAndUserId(Long id, Long userId);
}