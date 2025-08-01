package com.cptvolt.expensetracker.dto;

import com.cptvolt.expensetracker.model.Category;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ExpenseResponseDTO {

    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private Category category;
}
