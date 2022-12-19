package com.dudev.jdbc.starter.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Guitar {

    private UUID id;
    private String model;
    private String description;
    private int year;
    private String country;
    private String pickUps;
    private String wood;
    private String media;
    private LocalDateTime timestamp;
    private User user;
    private double price;
    private Brand brand;
    private boolean isClosed;
    private ChangeType changeType;
    private double changeValue;
    private String changeWish;

    public Guitar(UUID id, String model, String description, int year, String country, String pickUps, String wood, String media, LocalDateTime timestamp, User user, double price, Brand brand, boolean isClosed, ChangeType changeType, double changeValue, String changeWish) {
        this.id = id;
        this.model = model;
        this.description = description;
        this.year = year;
        this.country = country;
        this.pickUps = pickUps;
        this.wood = wood;
        this.media = media;
        this.timestamp = timestamp;
        this.user = user;
        this.price = price;
        this.brand = brand;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPickUps() {
        return pickUps;
    }

    public void setPickUps(String pickUps) {
        this.pickUps = pickUps;
    }

    public String getWood() {
        return wood;
    }

    public void setWood(String wood) {
        this.wood = wood;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
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

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
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
}
