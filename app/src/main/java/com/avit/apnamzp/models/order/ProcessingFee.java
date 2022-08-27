package com.avit.apnamzp.models.order;

public class ProcessingFee {
    int init;
    int inc;
    int jump;

    public ProcessingFee(int init, int inc, int jump) {
        this.init = init;
        this.inc = inc;
        this.jump = jump;
    }

    public int getInit() {
        return init;
    }

    public int getInc() {
        return inc;
    }

    public int getJump() {
        return jump;
    }

    @Override
    public String toString() {
        return "ProcessingFee{" +
                "init=" + init +
                ", inc=" + inc +
                ", jump=" + jump +
                '}';
    }
}
