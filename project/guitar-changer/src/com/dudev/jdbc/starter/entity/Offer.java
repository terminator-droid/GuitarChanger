package com.dudev.jdbc.starter.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Offer {

    private UUID id;
    private User buyer;
    private Product interchange;
    private ChangeType changeType;
    private double changeValue;
    private LocalDateTime timestamp;

    public Offer(UUID id, User buyer, Product interchange, ChangeType changeType, double changeValue, LocalDateTime timestamp) {
        this.id = id;
        this.buyer = buyer;
        this.interchange = interchange;
        this.changeType = changeType;
        this.changeValue = changeValue;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Product getInterchange() {
        return interchange;
    }

    public void setInterchange(Product interchange) {
        this.interchange = interchange;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
