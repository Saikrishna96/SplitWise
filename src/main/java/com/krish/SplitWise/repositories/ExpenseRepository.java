package com.krish.SplitWise.repositories;

import com.krish.SplitWise.models.expenses.Expense;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpenseRepository {

    private final List<Expense> expenses;

    public ExpenseRepository(){
        this.expenses = new ArrayList<>();
    }

    public void save(Expense expense) {
        if (expense != null) {
            expenses.add(expense);
        }
    }

}
