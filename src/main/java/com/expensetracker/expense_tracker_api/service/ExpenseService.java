package com.expensetracker.expense_tracker_api.service;

import com.expensetracker.expense_tracker_api.dtorequest.ExpenseRequest;
import com.expensetracker.expense_tracker_api.dtoresponse.ExpenseResponse;
import com.expensetracker.expense_tracker_api.entity.Expense;
import com.expensetracker.expense_tracker_api.entity.User;
import com.expensetracker.expense_tracker_api.exception.ResourceNotFoundException;
import com.expensetracker.expense_tracker_api.mapper.ExpenseMapper;
import com.expensetracker.expense_tracker_api.repository.ExpenseRepository;
import com.expensetracker.expense_tracker_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ExpenseMapper expenseMapper;

    private User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));
    }

    public ExpenseResponse createExpense(ExpenseRequest request, Authentication authentication) {
        User user = getCurrentUser(authentication);

        Expense expense = Expense.builder()
                .title(request.getTitle())
                .amount(request.getAmount())
                .category(request.getCategory())
                .description(request.getDescription())
                .expenseDate(request.getExpenseDate())
                .user(user)
                .build();

        Expense saved = expenseRepository.save(expense);
        return expenseMapper.toResponse(saved);
    }

    public Page<ExpenseResponse> getExpenses(Authentication authentication, Pageable pageable) {
        User user = getCurrentUser(authentication);
        Page<Expense> expenses = expenseRepository.findByUserId(user.getId(), pageable);
        return expenses.map(expenseMapper::toResponse);
    }

    public ExpenseResponse getExpenseById(Long id, Authentication authentication) {
        User user = getCurrentUser(authentication);
        Expense expense = expenseRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        return expenseMapper.toResponse(expense);
    }

    public ExpenseResponse updateExpense(Long id, ExpenseRequest request, Authentication authentication) {
        User user = getCurrentUser(authentication);
        Expense expense = expenseRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());
        expense.setExpenseDate(request.getExpenseDate());

        Expense updated = expenseRepository.save(expense);
        return expenseMapper.toResponse(updated);
    }

    public void deleteExpense(Long id, Authentication authentication) {
        User user = getCurrentUser(authentication);
        Expense expense = expenseRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        expenseRepository.delete(expense);
    }
}