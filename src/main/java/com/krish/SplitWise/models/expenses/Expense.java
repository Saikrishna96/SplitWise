package com.krish.SplitWise.models.expenses;

import com.krish.SplitWise.models.ExpenseMetaData;
import com.krish.SplitWise.models.splits.Split;
import lombok.Data;

import java.util.List;

@Data
public abstract class Expense {
    private String id;
    private double amount;
    private String paidBy;
    private List<Split> splits;
    private ExpenseMetaData metaData;

    public Expense(double amount, String paidBy, List<Split> splits, ExpenseMetaData metaData) {
        this.amount = amount;
        this.paidBy = paidBy;
        this.splits = splits;
        this.metaData = metaData;
    }

    public abstract boolean validate();
}
