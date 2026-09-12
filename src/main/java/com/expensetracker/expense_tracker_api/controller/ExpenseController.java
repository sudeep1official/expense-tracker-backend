package com.expensetracker.expense_tracker_api.controller;

import com.expensetracker.expense_tracker_api.dtorequest.ExpenseRequest;
import com.expensetracker.expense_tracker_api.dtoresponse.ExpenseResponse;
import com.expensetracker.expense_tracker_api.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(
            @Valid @RequestBody ExpenseRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(expenseService.createExpense(request, authentication));
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseResponse>> getExpenses(
            Authentication authentication,
            @PageableDefault(size = 10, sort = "expenseDate") Pageable pageable) {
        Page<ExpenseResponse> response = expenseService.getExpenses(authentication, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseById(
            @PathVariable Long id,
            Authentication authentication) {
        ExpenseResponse response = expenseService.getExpenseById(id, authentication);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(expenseService.updateExpense(id, request, authentication));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(
            @PathVariable Long id,
            Authentication authentication) {
        expenseService.deleteExpense(id, authentication);
        return ResponseEntity.noContent().build();
    }
}