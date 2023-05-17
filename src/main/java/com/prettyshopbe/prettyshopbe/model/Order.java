package com.prettyshopbe.prettyshopbe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "addpress")
    private String addpress;

    @Column (name= "shipcod")
    private Boolean shipcod;

    //chua tra = false or da tra = true
    @Column(name = "status_payment")
    private Boolean statuspayment;


    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "session_id")
    private String sessionId;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Order() {
    }


    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSessionId() {
        return sessionId;
    }

    @Column(name = "status")
    private String status;

    public String getStatus() {
        return status;
    }

    public String setStatus(String status) {
        this.status = status;
        return status;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public Boolean getShipcod() {
        return shipcod;
    }

    public void setShipcod(Boolean shipcod) {
        this.shipcod = shipcod;
    }
}
