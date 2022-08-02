package com.avit.apnamzp.models.payment;

public class OnlinePaymentOrderIdPostData {
    private int amount;
    private String userPhoneNo;

    public OnlinePaymentOrderIdPostData(int amount, String userPhoneNo) {
        this.amount = amount;
        this.userPhoneNo = userPhoneNo;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public int getAmount() {
        return amount;
    }
}
