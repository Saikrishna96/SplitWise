package com.krish.SplitWise.models.splits;

import com.krish.SplitWise.models.User;
import lombok.Data;

@Data
public class PercentSplit extends Split {
    private double percent;

    public PercentSplit(User user, double percent) {
        super(user);
        this.percent = percent;
    }
}
