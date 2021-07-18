package com.leonardo.demos.avaloqassessment.model.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RollPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "time_stamp")
    private Long timestamp;

    @Column(name = "roll_sum")
    private Long rollSum;


    public RollPK() {
    }

    public RollPK(Long timestamp, Long rollSum) {
        this.timestamp = timestamp;
        this.rollSum = rollSum;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getRollSum() {
        return rollSum;
    }

    public void setRollSum(Long rollSum) {
        this.rollSum = rollSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RollPK)) return false;
        RollPK rollPK = (RollPK) o;
        return getTimestamp().equals(rollPK.getTimestamp()) &&
                getRollSum().equals(rollPK.getRollSum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimestamp(), getRollSum());
    }
}
