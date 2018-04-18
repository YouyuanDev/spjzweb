package com.spjzweb.entity;

public class DynamicMeasurementItem {

    private int id;
    private String measure_item_code;
    private float item_max_value;
    private float item_min_value;
    private float item_frequency;
    private String thread_acceptance_criteria_no;


    public DynamicMeasurementItem() {
    }

    public DynamicMeasurementItem(int id, String measure_item_code, float item_max_value, float item_min_value, float item_frequency, String thread_acceptance_criteria_no) {
        this.id = id;
        this.measure_item_code = measure_item_code;
        this.item_max_value = item_max_value;
        this.item_min_value = item_min_value;
        this.item_frequency = item_frequency;
        this.thread_acceptance_criteria_no = thread_acceptance_criteria_no;
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

    public float getItem_max_value() {
        return item_max_value;
    }

    public void setItem_max_value(float item_max_value) {
        this.item_max_value = item_max_value;
    }

    public float getItem_min_value() {
        return item_min_value;
    }

    public void setItem_min_value(float item_min_value) {
        this.item_min_value = item_min_value;
    }

    public float getItem_frequency() {
        return item_frequency;
    }

    public void setItem_frequency(float item_frequency) {
        this.item_frequency = item_frequency;
    }

    public String getThread_acceptance_criteria_no() {
        return thread_acceptance_criteria_no;
    }

    public void setThread_acceptance_criteria_no(String thread_acceptance_criteria_no) {
        this.thread_acceptance_criteria_no = thread_acceptance_criteria_no;
    }
}
