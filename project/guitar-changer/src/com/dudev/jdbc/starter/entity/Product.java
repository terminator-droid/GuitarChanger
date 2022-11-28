package com.dudev.jdbc.starter.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Product {

    private UUID id;
    private LocalDateTime timestamp;
    private User user;
    private Double price;
    private boolean isClosed;
    private ChangeType changeType;
    private double changeValue;
    private String changeWish;

    public Product(UUID id, LocalDateTime timestamp, User user, Double price, boolean isClosed, ChangeType changeType, double changeValue, String changeWish) {
        this.id = id;
        this.timestamp = timestamp;
        this.user = user;
        this.price = price;
        this.isClosed = isClosed;
        this.changeType = changeType;
        this.changeValue = changeValue;
        this.changeWish = changeWish;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public double getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(double changeValue) {
        this.changeValue = changeValue;
    }

    public String getChangeWish() {
        return changeWish;
    }

    public void setChangeWish(String changeWish) {
        this.changeWish = changeWish;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", user='" + user + '\'' +
                ", price=" + price +
                ", isClosed=" + isClosed +
                ", changeType=" + changeType +
                ", changeValue=" + changeValue +
                ", changeWish='" + changeWish + '\'' +
                '}';
    }
}
