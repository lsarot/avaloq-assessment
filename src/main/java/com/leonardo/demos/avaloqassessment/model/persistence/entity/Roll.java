package com.leonardo.demos.avaloqassessment.model.persistence.entity;

import javax.persistence.*;

@Entity(name = "roll")
//@Table(name = "roll")
public class Roll {

    @EmbeddedId
    protected RollPK rollPK;

    private Long count;

    public Roll() {
    }

    public Roll(RollPK rollPK, Long count) {
        this.rollPK = rollPK;
        this.count = count;
    }

    public RollPK getRollPK() {
        return rollPK;
    }

    public void setRollPK(RollPK rollPK) {
        this.rollPK = rollPK;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
