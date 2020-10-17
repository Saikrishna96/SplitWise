package com.krish.SplitWise.services;

import com.krish.SplitWise.models.ExpenseMetaData;
import com.krish.SplitWise.models.ExpenseType;
import com.krish.SplitWise.models.User;
import com.krish.SplitWise.models.expenses.Expense;
import com.krish.SplitWise.models.splits.Split;
import com.krish.SplitWise.repositories.BalanceRepository;
import com.krish.SplitWise.repositories.ExpenseRepository;
import com.krish.SplitWise.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SplitWiseService {
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final BalanceRepository balanceRepository;
    private final ExpenseCreator expenseCreator;

    public SplitWiseService(UserRepository userRepository, ExpenseRepository expenseRepository,
                            BalanceRepository balanceRepository, ExpenseCreator expenseCreator) {
        this.userRepository = userRepository;
        this.balanceRepository = balanceRepository;
        this.expenseRepository = expenseRepository;
        this.expenseCreator = expenseCreator;
    }

    public void createUser(User user) {
        userRepository.save(user);
        balanceRepository.startUserBalance(user);
    }

    public void addExpense(ExpenseType expenseType, double amount, String paidBy, List<Split> splits, ExpenseMetaData metaData){
        log.info("Adding expense in service");
        Expense expense = expenseCreator.createExpense(expenseType, paidBy, splits,amount, metaData);
//        if (!expense.validate()){
//            log.error("Validation failed");
//            return;
//        }
        expenseRepository.save(expense);
        balanceRepository.updateBalances(expense);
    }

    public void showBalances(){
        Map<String, Map<String, Double>> balanceSheet = balanceRepository.getBalanceSheet();
        boolean IsEmpty = true;
        for (Map.Entry<String, Map<String, Double>> allBalances : balanceSheet.entrySet()){
            for (Map.Entry<String, Double> userBalance : allBalances.getValue().entrySet()){
                if (userBalance.getValue() != 0.0)
                    IsEmpty = false;
                printBalance(allBalances.getKey(), userBalance.getKey(), userBalance.getValue());
            }
        }

        if (IsEmpty)
            System.out.println("No balances");

    }

    public User getUser(String userId){
        return userRepository.getUser(userId);
    }

    public void showBalance(String userId){
        log.info("showBalance for : {}", userId);
        Map<String, Map<String, Double>> balanceSheet = balanceRepository.getBalanceSheet();
        boolean IsEmpty = true;
        for (Map.Entry<String, Double> userBalance : balanceSheet.get(userId).entrySet()){
            if (userBalance.getValue() != 0.0)
                IsEmpty = false;
            printBalance(userId, userBalance.getKey(), userBalance.getValue());
        }

        if (IsEmpty)
            System.out.println("No balances");
    }


    private void printBalance(String u1, String u2, double amount){
        if (amount > 0){
            System.out.println(u2 + " owes " + u1 + " : " + Math.abs(amount));
        } else if (amount < 0 ) {
            System.out.println(u1 + " owes " + u2 + " : " + Math.abs(amount));
        }
    }

}
