package com.krish.SplitWise.models.splits;

import com.krish.SplitWise.models.User;
import lombok.Data;

// Split has all the info of a split required to create amount later.
@Data
public abstract class Split {
    private User user;
    double amount;

    public Split(User user){
        this.user = user;
    }
}
