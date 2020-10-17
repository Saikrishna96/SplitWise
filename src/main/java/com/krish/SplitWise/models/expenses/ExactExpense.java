package com.krish.SplitWise.models.expenses;

import com.krish.SplitWise.models.ExpenseMetaData;
import com.krish.SplitWise.models.splits.ExactSplit;
import com.krish.SplitWise.models.splits.Split;

import java.util.List;

public class ExactExpense extends Expense {

    public ExactExpense(double amount, String paidBy, List<Split> splits, ExpenseMetaData metaData) {
        super(amount, paidBy, splits, metaData);
    }

    @Override
    public boolean validate() {
        for (Split split : getSplits()) {
            if (!(split instanceof ExactSplit))
                return false;
        }

        // total amount should be sum of all split amounts
        double splitAmounts = 0.0;
        for (Split split : getSplits()) {
            ExactSplit exactSplit = (ExactSplit) split;
            splitAmounts += exactSplit.getAmount();
        }
        if (getAmount() != splitAmounts)
            return false;
        return true;
    }
}
