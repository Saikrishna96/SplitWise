package com.krish.SplitWise.models.expenses;

import com.krish.SplitWise.models.ExpenseMetaData;
import com.krish.SplitWise.models.splits.PercentSplit;
import com.krish.SplitWise.models.splits.Split;

import java.util.List;

public class PercentExpense extends Expense {

    public PercentExpense(double amount, String paidBy, List<Split> splits, ExpenseMetaData metaData) {
        super(amount, paidBy, splits, metaData);
    }

    @Override
    public boolean validate() {
        for (Split split : getSplits()) {
            if (!(split instanceof PercentSplit))
                return false;
        }

        // total percent should be sum of all split percents
        double totalPercents = 0.0;
        for (Split split : getSplits()) {
            PercentSplit percentSplit = (PercentSplit) split;
            totalPercents += percentSplit.getPercent();
        }
        if (totalPercents != 100)
            return false;
        return true;
    }
}
