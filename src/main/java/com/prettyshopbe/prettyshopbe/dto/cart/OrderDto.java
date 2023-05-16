package com.prettyshopbe.prettyshopbe.dto.cart;

import jakarta.persistence.Column;

public class OrderDto {
    @Column(name = "phone")
    private String phone;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "addpress")
    private String addpress;

    @Column(name = "status_payment")
    private Boolean statuspayment;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddpress() {
        return addpress;
    }

    public void setAddpress(String addpress) {
        this.addpress = addpress;
    }

    public Boolean getStatuspayment() {
        return statuspayment;
    }

    public void setStatuspayment(Boolean statuspayment) {
        this.statuspayment = statuspayment;
    }
}
