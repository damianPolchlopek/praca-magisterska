package com.polchlopek.praca.magisterska.DTO;

public class DescriptionFile {
    private static DescriptionFile ourInstance = new DescriptionFile();

    public static DescriptionFile getInstance() {
        return ourInstance;
    }

    private String description;
    private String category;
    private String axisX;
    private String axisY;

    private DescriptionFile() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAxisX() {
        return axisX;
    }

    public void setAxisX(String axisX) {
        this.axisX = axisX;
    }

    public String getAxisY() {
        return axisY;
    }

    public void setAxisY(String axisY) {
        this.axisY = axisY;
    }
}
