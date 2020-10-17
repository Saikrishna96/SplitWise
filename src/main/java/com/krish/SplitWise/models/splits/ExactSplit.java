package com.krish.SplitWise.models.splits;

import com.krish.SplitWise.models.User;
import lombok.Data;

@Data
public class ExactSplit extends Split {

    public ExactSplit(User user, double amount) {
        super(user);
        this.amount = amount;
    }
}
