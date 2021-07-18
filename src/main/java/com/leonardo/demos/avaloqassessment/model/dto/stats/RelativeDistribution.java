package com.leonardo.demos.avaloqassessment.model.dto.stats;

import java.io.Serializable;

public class RelativeDistribution implements Serializable {

    private static final long serialVersionUID = 1L;

    private long rollSum;
    private long count;
    private float percentage;

    public RelativeDistribution() {
    }

    public RelativeDistribution(long rollSum, long count, float percentage) {
        this.rollSum = rollSum;
        this.count = count;
        this.percentage = percentage;
    }

    public long getRollSum() {
        return rollSum;
    }

    public void setRollSum(long rollSum) {
        this.rollSum = rollSum;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
