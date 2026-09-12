package com.expensetracker.expense_tracker_api.dtoresponse;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ExpenseResponse {
    private Long id;
    private String title;
    private BigDecimal amount;
    private String category;
    private String description;
    private LocalDate expenseDate;
    private LocalDateTime createdAt;
}