package com.example.db_mdudash.model;

public class Room {
    private int id;
    private int dormitoryId;
    private String number;
    private int beds;
    private int area;
    private String equipment;
    private double price;
    private boolean isOccupied;

    public Room(boolean isOccupied, double price, String equipment, int area, int beds, String number, int dormitoryId, int id) {
        this.isOccupied = isOccupied;
        this.price = price;
        this.equipment = equipment;
        this.area = area;
        this.beds = beds;
        this.number = number;
        this.dormitoryId = dormitoryId;
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public int getDormitoryId() {
        return dormitoryId;
    }

    public void setDormitoryId(int dormitoryId) {
        this.dormitoryId = dormitoryId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}
