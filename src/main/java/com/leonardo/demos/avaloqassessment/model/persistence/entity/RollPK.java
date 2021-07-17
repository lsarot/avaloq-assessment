package com.leonardo.demos.avaloqassessment.model.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

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
}
