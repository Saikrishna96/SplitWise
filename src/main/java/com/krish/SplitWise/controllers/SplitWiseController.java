package com.krish.SplitWise.controllers;

import com.krish.SplitWise.models.ExpenseType;
import com.krish.SplitWise.models.RequestDto;
import com.krish.SplitWise.models.User;
import com.krish.SplitWise.models.splits.EqualSplit;
import com.krish.SplitWise.models.splits.ExactSplit;
import com.krish.SplitWise.models.splits.PercentSplit;
import com.krish.SplitWise.models.splits.Split;
import com.krish.SplitWise.services.SplitWiseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
@Slf4j
public class SplitWiseController {
    // source
    // https://workat.tech/machine-coding/article/how-to-design-splitwise-machine-coding-ayvnfo1tfst6
    private final SplitWiseService splitWiseService;

    SplitWiseController(SplitWiseService splitWiseService) {
        this.splitWiseService = splitWiseService;
    }

    @PostMapping("add-user")
    public void addUser(@RequestBody User user) {
        splitWiseService.createUser(user);
    }

    @PostMapping("add-expense")
    public void addExpense(@RequestBody RequestDto input) {
        log.info("Add expense command : {}", input.getInput());
        String[] commands = input.getInput().split(" ");
        String commandType = commands[0];
        log.info("commandtype : {}", commandType);

        switch (commandType) {
            case "SHOW":
                log.info("Showing balance");
                if (commands.length == 1) {
                    splitWiseService.showBalances();
                } else {
                    splitWiseService.showBalance(commands[1]);
                }
                break;
            case "EXPENSE":
                log.info("Adding expense");
                String paidBy = commands[1];
                Double amount = Double.parseDouble(commands[2]);
                int noOfUsers = Integer.parseInt(commands[3]);
                String expenseType = commands[4 + noOfUsers];
                List<Split> splits = new ArrayList<>();
                switch (expenseType) {
                    case "EQUAL":
                        for (int i = 0; i < noOfUsers; i++) {
                            splits.add(new EqualSplit(splitWiseService.getUser(commands[4 + i])));
                        }
                        splitWiseService.addExpense(ExpenseType.EQUAL, amount, paidBy, splits, null);
                        break;
                    case "EXACT":
                        for (int i = 0; i < noOfUsers; i++) {
                            splits.add(new ExactSplit(splitWiseService.getUser(commands[4 + i]), Double.parseDouble(commands[5 + noOfUsers + i])));
                        }
                        splitWiseService.addExpense(ExpenseType.EXACT, amount, paidBy, splits, null);
                        break;
                    case "PERCENT":
                        for (int i = 0; i < noOfUsers; i++) {
                            splits.add(new PercentSplit(splitWiseService.getUser(commands[4 + i]), Double.parseDouble(commands[5 + noOfUsers + i])));
                        }
                        splitWiseService.addExpense(ExpenseType.PERCENT, amount, paidBy, splits, null);
                        break;
                }
                break;
        }
    }
}
