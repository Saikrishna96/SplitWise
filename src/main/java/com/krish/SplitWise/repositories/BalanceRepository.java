package com.krish.SplitWise.repositories;

import com.krish.SplitWise.models.User;
import com.krish.SplitWise.models.expenses.Expense;
import com.krish.SplitWise.models.splits.Split;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class BalanceRepository {

    private final Map<String, Map<String, Double>> balanceSheet;

    public BalanceRepository() {
        this.balanceSheet = new HashMap<>();
    }

    public void updateBalances(Expense expense) {
        if (expense != null) {
            String paidBy = expense.getPaidBy();
            log.info("paidby : {}", paidBy);
            List<Split> splits = expense.getSplits();
            for (Split split : splits) {
                String paidTo = split.getUser().getId();
                if (paidBy.equals(paidTo))
                    continue;
                log.info("paidTo : {}", paidTo);
                Map<String, Double> balances = balanceSheet.get(paidTo);
                if (!balances.containsKey(paidBy))
                    balances.put(paidBy, 0.0);
                balances.put(paidBy, balances.get(paidBy) - split.getAmount());
                log.info(paidTo + " : " + paidBy + " -> " + balances.get(paidBy));

                balances = balanceSheet.get(paidBy);
                if (!balances.containsKey(paidTo))
                    balances.put(paidTo, 0.0);

                balances.put(paidTo, balances.get(paidTo) + split.getAmount());
                log.info(paidBy + " : " + paidTo + " -> " + balances.get(paidTo));
            }
        }
    }

    public Map<String, Map<String, Double>> getBalanceSheet(){
        return balanceSheet;
    }

    public void startUserBalance(User user) {
        balanceSheet.put(user.getId(), new HashMap<>());
        log.info("user balance initiated for : {}", user.getId());
    }

}
