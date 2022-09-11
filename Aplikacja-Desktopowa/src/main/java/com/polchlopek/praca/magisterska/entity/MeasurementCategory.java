package com.polchlopek.praca.magisterska.entity;

import javax.persistence.*;

@Entity
@Table(name="meas_category")
public class MeasurementCategory {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="category")
    private String category;

    @Column(name="description_axis_x")
    private String descriptionAxisX;

    @Column(name="description_axis_y")
    private String descriptionAxisY;



    public MeasurementCategory() {

    }

    public MeasurementCategory(String category, String descriptionAxisX, String descriptionAxisY) {
        this.category = category;
        this.descriptionAxisX = descriptionAxisX;
        this.descriptionAxisY = descriptionAxisY;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescriptionAxisX() {
        return descriptionAxisX;
    }

    public void setDescriptionAxisX(String descriptionAxisX) {
        this.descriptionAxisX = descriptionAxisX;
    }

    public String getDescriptionAxisY() {
        return descriptionAxisY;
    }

    public void setDescriptionAxisY(String descriptionAxisY) {
        this.descriptionAxisY = descriptionAxisY;
    }


    @Override
    public String toString() {
        return "MeasurementCategory{" +
                "category=" + category +
                '}';
    }
}
