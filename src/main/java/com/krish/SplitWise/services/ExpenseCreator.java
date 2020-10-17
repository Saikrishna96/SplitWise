package com.krish.SplitWise.services;

import com.krish.SplitWise.models.ExpenseMetaData;
import com.krish.SplitWise.models.ExpenseType;
import com.krish.SplitWise.models.expenses.EqualExpense;
import com.krish.SplitWise.models.expenses.ExactExpense;
import com.krish.SplitWise.models.expenses.Expense;
import com.krish.SplitWise.models.expenses.PercentExpense;
import com.krish.SplitWise.models.splits.PercentSplit;
import com.krish.SplitWise.models.splits.Split;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseCreator {

    // while creating expense make sure splits has right amount set in it.
    public Expense createExpense(ExpenseType expenseType, String paidBy, List<Split> splits, double amount, ExpenseMetaData metaData) {
        switch (expenseType) {
            case EQUAL:
                int totalSplits = splits.size();
                double splitAmount = Math.round((amount * 100) / totalSplits) / 100.0;
                for (Split split : splits) {
                    split.setAmount(splitAmount);
                }
                splits.get(0).setAmount(splitAmount + amount - (splitAmount * totalSplits));
                return new EqualExpense(amount, paidBy, splits, metaData);
            case EXACT: // amount is already set, so no change in splits
                return new ExactExpense(amount, paidBy, splits, metaData);
            case PERCENT:
                for (Split split : splits) {
                    PercentSplit percentSplit = (PercentSplit) split;
                    split.setAmount(amount * percentSplit.getPercent() / 100);
                }
                return new PercentExpense(amount, paidBy, splits, metaData);
            default:
                return null;
        }
    }
}
