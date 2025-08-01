package com.cptvolt.expensetracker.service;

import com.cptvolt.expensetracker.dto.ExpenseRequestDTO;
import com.cptvolt.expensetracker.dto.ExpenseResponseDTO;
import com.cptvolt.expensetracker.model.Expense;
import com.cptvolt.expensetracker.model.User;
import com.cptvolt.expensetracker.repository.ExpenseRepository;
import com.cptvolt.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;


    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public ExpenseResponseDTO addExpense(ExpenseRequestDTO dto, String username) {

        User user = tryFindUser(username);

        Expense expense = Expense.builder()
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .category(dto.getCategory())
                .user(user)
                .build();

        Expense savedExpense = expenseRepository.save(expense);

        return toResponse(savedExpense);
    }

    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO dto, String username) {

        tryFindUser(username);

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException
                        ("Expense not found or unauthorized access. Please double-check what you are asking."));

        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());
        expense.setCategory(dto.getCategory());

        return toResponse(expenseRepository.save(expense));
    }

    public void deleteExpense(Long id, String username) {

        tryFindUser(username);

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException
                        ("Expense not found or unauthorized access. Please double-check what you are asking."));

        expenseRepository.delete(expense);
    }

    private ExpenseResponseDTO toResponse(Expense expense) {
        return ExpenseResponseDTO.builder()
                .id(expense.getId())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .category(expense.getCategory())
                .build();
    }

    public List<ExpenseResponseDTO> getUserExpenses(String username) {

        User user = tryFindUser(username);

        return expenseRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ExpenseResponseDTO> getExpensesByDateRange(String username, LocalDate start, LocalDate end) {

        User user = tryFindUser(username);

        return expenseRepository.findByUserAndDateBetween(user, start, end)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Map<String, BigDecimal> getCategorySummary(String username, int month, int year) {

        User user = tryFindUser(username);

        List<Object[]> result = expenseRepository.getCategorySummary(user, month, year);

        Map<String, BigDecimal> summary = new HashMap<>();

        for (Object[] row: result) {
            String category = row[0].toString();
            BigDecimal total = (BigDecimal) row[1];
            summary.put(category, total);
        }

        return summary;
    }

    // DRY Code.
    private User tryFindUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found!"));
    }
}
