package com.avit.apnamzp.models.payment;

import com.avit.apnamzp.models.order.OrderItem;

public class OnlinePaymentOrderIdPostData {
    private int amount;
    private String userPhoneNo;
    private OrderItem orderItem;

    public OnlinePaymentOrderIdPostData(int amount, String userPhoneNo, OrderItem orderItem) {
        this.amount = amount;
        this.userPhoneNo = userPhoneNo;
        this.orderItem = orderItem;
    }

    public OnlinePaymentOrderIdPostData(int amount, String userPhoneNo) {
        this.amount = amount;
        this.userPhoneNo = userPhoneNo;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public int getAmount() {
        return amount;
    }
}
