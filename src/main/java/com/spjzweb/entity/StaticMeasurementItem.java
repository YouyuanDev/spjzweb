package com.spjzweb.entity;

public class StaticMeasurementItem {

    private int id;
    private String measure_item_code;
    private String measure_item_name;
    private String measure_item_name_en;
    private String measure_tool1;
    private String measure_tool2;
    private String measure_unit;
    private float  item_frequency;


    public StaticMeasurementItem() {
    }


    public StaticMeasurementItem(int id, String measure_item_code, String measure_item_name, String measure_item_name_en, String measure_tool1, String measure_tool2,String measure_unit, float item_frequency) {
        this.id = id;
        this.measure_item_code = measure_item_code;
        this.measure_item_name = measure_item_name;
        this.measure_item_name_en = measure_item_name_en;
        this.measure_tool1 = measure_tool1;
        this.measure_tool2 = measure_tool2;
        this.measure_unit = measure_unit;
        this.item_frequency = item_frequency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeasure_item_code() {
        return measure_item_code;
    }

    public void setMeasure_item_code(String measure_item_code) {
        this.measure_item_code = measure_item_code;
    }

    public String getMeasure_item_name() {
        return measure_item_name;
    }

    public void setMeasure_item_name(String measure_item_name) {
        this.measure_item_name = measure_item_name;
    }

    public String getMeasure_item_name_en() {
        return measure_item_name_en;
    }

    public void setMeasure_item_name_en(String measure_item_name_en) {
        this.measure_item_name_en = measure_item_name_en;
    }

    public String getMeasure_tool1() {
        return measure_tool1;
    }

    public void setMeasure_tool1(String measure_tool1) {
        this.measure_tool1 = measure_tool1;
    }

    public String getMeasure_tool2() {
        return measure_tool2;
    }

    public void setMeasure_tool2(String measure_tool2) {
        this.measure_tool2 = measure_tool2;
    }

    public String getMeasure_unit() {
        return measure_unit;
    }

    public void setMeasure_unit(String measure_unit) {
        this.measure_unit = measure_unit;
    }

    public float getItem_frequency() {
        return item_frequency;
    }

    public void setItem_frequency(float item_frequency) {
        this.item_frequency = item_frequency;
    }
}
