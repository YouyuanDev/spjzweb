package com.spjzweb.entity;

import java.util.Date;

public class ThreadingProcess {
    private int id;
    private String couping_no;
    private String process_no;
    private String operator_no;
    private Date inspection_time;
    private String visual_inspection;
    private float thread_tooth_pitch_diameter_max;
    private float thread_tooth_pitch_diameter_avg;
    private float thread_tooth_pitch_diameter_min;
    private float thread_sealing_surface_diameter_max;
    private float thread_sealing_surface_diameter_avg;
    private float thread_sealing_surface_diameter_min;
    private float thread_sealing_surface_ovality;
    private float thread_width;
    private float thread_pitch;
    private float thread_taper;
    private float thread_height;
    private float thread_length_min;
    private float thread_bearing_surface_width;
    private float couping_inner_end_depth;
    private float thread_hole_inner_diameter;
    private float couping_od;
    private float couping_length;
    private float thread_tooth_angle;
    private float thread_boring_size;
    private float thread_throug_hole_size;
    private String video_no;
    private String tool_measuring_record_no;
    private String inspection_result;

    public ThreadingProcess() {
    }

    public ThreadingProcess(int id, String couping_no, String process_no, String operator_no, Date inspection_time, String visual_inspection, float thread_tooth_pitch_diameter_max, float thread_tooth_pitch_diameter_avg, float thread_tooth_pitch_diameter_min, float thread_sealing_surface_diameter_max, float thread_sealing_surface_diameter_avg, float thread_sealing_surface_diameter_min, float thread_sealing_surface_ovality, float thread_width, float thread_pitch, float thread_taper, float thread_height, float thread_length_min, float thread_bearing_surface_width, float couping_inner_end_depth, float thread_hole_inner_diameter, float couping_od, float couping_length, float thread_tooth_angle, float thread_boring_size, float thread_throug_hole_size, String video_no, String tool_measuring_record_no, String inspection_result) {
        this.id = id;
        this.couping_no = couping_no;
        this.process_no = process_no;
        this.operator_no = operator_no;
        this.inspection_time = inspection_time;
        this.visual_inspection = visual_inspection;
        this.thread_tooth_pitch_diameter_max = thread_tooth_pitch_diameter_max;
        this.thread_tooth_pitch_diameter_avg = thread_tooth_pitch_diameter_avg;
        this.thread_tooth_pitch_diameter_min = thread_tooth_pitch_diameter_min;
        this.thread_sealing_surface_diameter_max = thread_sealing_surface_diameter_max;
        this.thread_sealing_surface_diameter_avg = thread_sealing_surface_diameter_avg;
        this.thread_sealing_surface_diameter_min = thread_sealing_surface_diameter_min;
        this.thread_sealing_surface_ovality = thread_sealing_surface_ovality;
        this.thread_width = thread_width;
        this.thread_pitch = thread_pitch;
        this.thread_taper = thread_taper;
        this.thread_height = thread_height;
        this.thread_length_min = thread_length_min;
        this.thread_bearing_surface_width = thread_bearing_surface_width;
        this.couping_inner_end_depth = couping_inner_end_depth;
        this.thread_hole_inner_diameter = thread_hole_inner_diameter;
        this.couping_od = couping_od;
        this.couping_length = couping_length;
        this.thread_tooth_angle = thread_tooth_angle;
        this.thread_boring_size = thread_boring_size;
        this.thread_throug_hole_size = thread_throug_hole_size;
        this.video_no = video_no;
        this.tool_measuring_record_no = tool_measuring_record_no;
        this.inspection_result = inspection_result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCouping_no() {
        return couping_no;
    }

    public void setCouping_no(String couping_no) {
        this.couping_no = couping_no;
    }

    public String getProcess_no() {
        return process_no;
    }

    public void setProcess_no(String process_no) {
        this.process_no = process_no;
    }

    public String getOperator_no() {
        return operator_no;
    }

    public void setOperator_no(String operator_no) {
        this.operator_no = operator_no;
    }

    public Date getInspection_time() {
        return inspection_time;
    }

    public void setInspection_time(Date inspection_time) {
        this.inspection_time = inspection_time;
    }

    public String getVisual_inspection() {
        return visual_inspection;
    }

    public void setVisual_inspection(String visual_inspection) {
        this.visual_inspection = visual_inspection;
    }

    public float getThread_tooth_pitch_diameter_max() {
        return thread_tooth_pitch_diameter_max;
    }

    public void setThread_tooth_pitch_diameter_max(float thread_tooth_pitch_diameter_max) {
        this.thread_tooth_pitch_diameter_max = thread_tooth_pitch_diameter_max;
    }

    public float getThread_tooth_pitch_diameter_avg() {
        return thread_tooth_pitch_diameter_avg;
    }

    public void setThread_tooth_pitch_diameter_avg(float thread_tooth_pitch_diameter_avg) {
        this.thread_tooth_pitch_diameter_avg = thread_tooth_pitch_diameter_avg;
    }

    public float getThread_tooth_pitch_diameter_min() {
        return thread_tooth_pitch_diameter_min;
    }

    public void setThread_tooth_pitch_diameter_min(float thread_tooth_pitch_diameter_min) {
        this.thread_tooth_pitch_diameter_min = thread_tooth_pitch_diameter_min;
    }

    public float getThread_sealing_surface_diameter_max() {
        return thread_sealing_surface_diameter_max;
    }

    public void setThread_sealing_surface_diameter_max(float thread_sealing_surface_diameter_max) {
        this.thread_sealing_surface_diameter_max = thread_sealing_surface_diameter_max;
    }

    public float getThread_sealing_surface_diameter_avg() {
        return thread_sealing_surface_diameter_avg;
    }

    public void setThread_sealing_surface_diameter_avg(float thread_sealing_surface_diameter_avg) {
        this.thread_sealing_surface_diameter_avg = thread_sealing_surface_diameter_avg;
    }

    public float getThread_sealing_surface_diameter_min() {
        return thread_sealing_surface_diameter_min;
    }

    public void setThread_sealing_surface_diameter_min(float thread_sealing_surface_diameter_min) {
        this.thread_sealing_surface_diameter_min = thread_sealing_surface_diameter_min;
    }

    public float getThread_sealing_surface_ovality() {
        return thread_sealing_surface_ovality;
    }

    public void setThread_sealing_surface_ovality(float thread_sealing_surface_ovality) {
        this.thread_sealing_surface_ovality = thread_sealing_surface_ovality;
    }

    public float getThread_width() {
        return thread_width;
    }

    public void setThread_width(float thread_width) {
        this.thread_width = thread_width;
    }

    public float getThread_pitch() {
        return thread_pitch;
    }

    public void setThread_pitch(float thread_pitch) {
        this.thread_pitch = thread_pitch;
    }

    public float getThread_taper() {
        return thread_taper;
    }

    public void setThread_taper(float thread_taper) {
        this.thread_taper = thread_taper;
    }

    public float getThread_height() {
        return thread_height;
    }

    public void setThread_height(float thread_height) {
        this.thread_height = thread_height;
    }

    public float getThread_length_min() {
        return thread_length_min;
    }

    public void setThread_length_min(float thread_length_min) {
        this.thread_length_min = thread_length_min;
    }

    public float getThread_bearing_surface_width() {
        return thread_bearing_surface_width;
    }

    public void setThread_bearing_surface_width(float thread_bearing_surface_width) {
        this.thread_bearing_surface_width = thread_bearing_surface_width;
    }

    public float getCouping_inner_end_depth() {
        return couping_inner_end_depth;
    }

    public void setCouping_inner_end_depth(float couping_inner_end_depth) {
        this.couping_inner_end_depth = couping_inner_end_depth;
    }

    public float getThread_hole_inner_diameter() {
        return thread_hole_inner_diameter;
    }

    public void setThread_hole_inner_diameter(float thread_hole_inner_diameter) {
        this.thread_hole_inner_diameter = thread_hole_inner_diameter;
    }

    public float getCouping_od() {
        return couping_od;
    }

    public void setCouping_od(float couping_od) {
        this.couping_od = couping_od;
    }

    public float getCouping_length() {
        return couping_length;
    }

    public void setCouping_length(float couping_length) {
        this.couping_length = couping_length;
    }

    public float getThread_tooth_angle() {
        return thread_tooth_angle;
    }

    public void setThread_tooth_angle(float thread_tooth_angle) {
        this.thread_tooth_angle = thread_tooth_angle;
    }

    public float getThread_boring_size() {
        return thread_boring_size;
    }

    public void setThread_boring_size(float thread_boring_size) {
        this.thread_boring_size = thread_boring_size;
    }

    public float getThread_throug_hole_size() {
        return thread_throug_hole_size;
    }

    public void setThread_throug_hole_size(float thread_throug_hole_size) {
        this.thread_throug_hole_size = thread_throug_hole_size;
    }

    public String getVideo_no() {
        return video_no;
    }

    public void setVideo_no(String video_no) {
        this.video_no = video_no;
    }

    public String getTool_measuring_record_no() {
        return tool_measuring_record_no;
    }

    public void setTool_measuring_record_no(String tool_measuring_record_no) {
        this.tool_measuring_record_no = tool_measuring_record_no;
    }

    public String getInspection_result() {
        return inspection_result;
    }

    public void setInspection_result(String inspection_result) {
        this.inspection_result = inspection_result;
    }
}
