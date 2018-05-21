package com.spjzweb.entity;

public class ItemRecord {

    private int id;
    private String itemcode;
    private String itemvalue;
    private String toolcode1;
    private String toolcode2;
    private String measure_sample1;
    private String measure_sample2;
    private String thread_inspection_record_code;

    private String reading_max;
    private String reading_min;
    private String reading_avg;
    private String reading_ovality;


    public ItemRecord() {
    }

    public ItemRecord(int id, String itemcode, String itemvalue, String toolcode1, String toolcode2, String measure_sample1, String measure_sample2, String thread_inspection_record_code, String reading_max, String reading_min, String reading_avg, String reading_ovality) {
        this.id = id;
        this.itemcode = itemcode;
        this.itemvalue = itemvalue;
        this.toolcode1 = toolcode1;
        this.toolcode2 = toolcode2;
        this.measure_sample1 = measure_sample1;
        this.measure_sample2 = measure_sample2;
        this.thread_inspection_record_code = thread_inspection_record_code;
        this.reading_max = reading_max;
        this.reading_min = reading_min;
        this.reading_avg = reading_avg;
        this.reading_ovality = reading_ovality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemvalue() {
        return itemvalue;
    }

    public void setItemvalue(String itemvalue) {
        this.itemvalue = itemvalue;
    }

    public String getToolcode1() {
        return toolcode1;
    }

    public void setToolcode1(String toolcode1) {
        this.toolcode1 = toolcode1;
    }

    public String getToolcode2() {
        return toolcode2;
    }

    public void setToolcode2(String toolcode2) {
        this.toolcode2 = toolcode2;
    }

    public String getMeasure_sample1() {
        return measure_sample1;
    }

    public void setMeasure_sample1(String measure_sample1) {
        this.measure_sample1 = measure_sample1;
    }

    public String getMeasure_sample2() {
        return measure_sample2;
    }

    public void setMeasure_sample2(String measure_sample2) {
        this.measure_sample2 = measure_sample2;
    }

    public String getThread_inspection_record_code() {
        return thread_inspection_record_code;
    }

    public void setThread_inspection_record_code(String thread_inspection_record_code) {
        this.thread_inspection_record_code = thread_inspection_record_code;
    }

    public String getReading_max() {
        return reading_max;
    }

    public void setReading_max(String reading_max) {
        this.reading_max = reading_max;
    }

    public String getReading_min() {
        return reading_min;
    }

    public void setReading_min(String reading_min) {
        this.reading_min = reading_min;
    }

    public String getReading_avg() {
        return reading_avg;
    }

    public void setReading_avg(String reading_avg) {
        this.reading_avg = reading_avg;
    }

    public String getReading_ovality() {
        return reading_ovality;
    }

    public void setReading_ovality(String reading_ovality) {
        this.reading_ovality = reading_ovality;
    }
}
