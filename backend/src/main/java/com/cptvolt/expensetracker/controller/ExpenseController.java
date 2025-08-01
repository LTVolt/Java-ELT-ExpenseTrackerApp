package com.cptvolt.expensetracker.controller;

import com.cptvolt.expensetracker.dto.ExpenseRequestDTO;
import com.cptvolt.expensetracker.dto.ExpenseResponseDTO;
import com.cptvolt.expensetracker.service.ExpenseService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @CrossOrigin
    @PostMapping
    public ExpenseResponseDTO addExpense(@RequestBody ExpenseRequestDTO dto, Authentication authentication) {
        return expenseService.addExpense(dto, authentication.getName());
    }

    @CrossOrigin
    @GetMapping
    public List<ExpenseResponseDTO> getExpense(Authentication authentication) {
        return expenseService.getUserExpenses(authentication.getName());
    }

    @CrossOrigin
    @GetMapping("/filter")
    public List<ExpenseResponseDTO> filterByDateRange
            (@RequestParam("start") String start, @RequestParam("end") String end, Authentication authentication) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return expenseService.getExpensesByDateRange(authentication.getName(), startDate, endDate);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ExpenseResponseDTO updateExpense
            (@PathVariable Long id, @RequestBody ExpenseRequestDTO dto, Authentication authentication) {
        return expenseService.updateExpense(id, dto, authentication.getName());
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public void ExpenseResponseDTO
            (@PathVariable Long id, Authentication authentication) {
        expenseService.deleteExpense(id, authentication.getName());
    }

    @CrossOrigin
    @GetMapping("/summary")
    public Map<String, BigDecimal> getSummary(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            Authentication auth
    ) {
        return expenseService.getCategorySummary(auth.getName(), month, year);
    }
}
