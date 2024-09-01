package com.example.database_test;

public class CAR
{
    private int id;
    private String model;
    private String color;
    private Double dbl;
    private String image;
    private String discription;

    public CAR(int id, String model, String color, Double dbl, String image, String discription)
    {
        this.id = id;
        this.model = model;
        this.color = color;
        this.dbl = dbl;
        this.image = image;
        this.discription = discription;
    }

    public CAR(String model, String color, Double dbl, String image, String discription)
    {
        this.model = model;
        this.color = color;
        this.dbl = dbl;
        this.image = image;
        this.discription = discription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getDbl() {
        return dbl;
    }

    public void setDbl(Double dbl) {
        this.dbl = dbl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}

