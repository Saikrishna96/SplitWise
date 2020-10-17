package com.krish.SplitWise.models.expenses;

import com.krish.SplitWise.models.ExpenseMetaData;
import com.krish.SplitWise.models.splits.EqualSplit;
import com.krish.SplitWise.models.splits.Split;

import java.util.List;

public class EqualExpense extends Expense {

    public EqualExpense(double amount, String paidBy, List<Split> splits, ExpenseMetaData metaData) {
        super(amount, paidBy, splits, metaData);
    }

    public boolean validate() {
        for (Split split : getSplits()) {
            if (!(split instanceof EqualSplit))
                return false;
        }
        return true;
    }
}
