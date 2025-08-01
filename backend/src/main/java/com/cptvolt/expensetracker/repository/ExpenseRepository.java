package com.cptvolt.expensetracker.repository;

import com.cptvolt.expensetracker.model.Expense;
import com.cptvolt.expensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(User user);

    List<Expense> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);

    @Query( "SELECT e.category, SUM(e.amount) " +
            "FROM Expense e " +
            "WHERE e.user = :user AND MONTH(e.date) = :month AND YEAR(e.date) = :year " +
            "GROUP BY e.category")
    List<Object[]> getCategorySummary(User user, int month, int year);
}
