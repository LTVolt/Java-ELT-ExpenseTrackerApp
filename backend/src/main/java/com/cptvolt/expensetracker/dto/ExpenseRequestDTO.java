package com.cptvolt.expensetracker.dto;

import com.cptvolt.expensetracker.model.Category;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseRequestDTO {

    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private Category category;
}
