package com.spjzweb.entity;

import java.util.Date;

public class ThreadAcceptanceCriteria {
    private int id;
    private String thread_acceptance_criteria_no;
    private float thread_pitch_diameter_max;
    private float thread_pitch_diameter_min;
    private float thread_sealing_surface_diameter_max;
    private  float thread_sealing_surface_diameter_min;
    private float thread_sealing_surface_ovality_max;
    private float thread_sealing_surface_ovality_min;
    private float thread_pitch_max;
    private float thread_pitch_min;
    private float thread_taper_max;
    private float thread_taper_min;
    private float thread_height_max;
    private float thread_height_min;
    private float thread_bearing_surface_width_max;
    private float thread_bearing_surface_width_min;
    private float couping_inner_end_depth_max;
    private float couping_inner_end_depth_min;
    private float thread_hole_inner_diameter_max;
    private float thread_hole_inner_diameter_min;
    private float couping_od_max;
    private float couping_od_min;
    private  float couping_length_max;
    private float couping_length_min;
    private Date last_update_time;
    public ThreadAcceptanceCriteria() {
    }

    public ThreadAcceptanceCriteria(int id, String thread_acceptance_criteria_no, float thread_pitch_diameter_max, float thread_pitch_diameter_min, float thread_sealing_surface_diameter_max, float thread_sealing_surface_diameter_min, float thread_sealing_surface_ovality_max, float thread_sealing_surface_ovality_min, float thread_pitch_max, float thread_pitch_min, float thread_taper_max, float thread_taper_min, float thread_height_max, float thread_height_min, float thread_bearing_surface_width_max, float thread_bearing_surface_width_min, float couping_inner_end_depth_max, float couping_inner_end_depth_min, float thread_hole_inner_diameter_max, float thread_hole_inner_diameter_min, float couping_od_max, float couping_od_min, float couping_length_max, float couping_length_min, Date last_update_time) {
        this.id = id;
        this.thread_acceptance_criteria_no = thread_acceptance_criteria_no;
        this.thread_pitch_diameter_max = thread_pitch_diameter_max;
        this.thread_pitch_diameter_min = thread_pitch_diameter_min;
        this.thread_sealing_surface_diameter_max = thread_sealing_surface_diameter_max;
        this.thread_sealing_surface_diameter_min = thread_sealing_surface_diameter_min;
        this.thread_sealing_surface_ovality_max = thread_sealing_surface_ovality_max;
        this.thread_sealing_surface_ovality_min = thread_sealing_surface_ovality_min;
        this.thread_pitch_max = thread_pitch_max;
        this.thread_pitch_min = thread_pitch_min;
        this.thread_taper_max = thread_taper_max;
        this.thread_taper_min = thread_taper_min;
        this.thread_height_max = thread_height_max;
        this.thread_height_min = thread_height_min;
        this.thread_bearing_surface_width_max = thread_bearing_surface_width_max;
        this.thread_bearing_surface_width_min = thread_bearing_surface_width_min;
        this.couping_inner_end_depth_max = couping_inner_end_depth_max;
        this.couping_inner_end_depth_min = couping_inner_end_depth_min;
        this.thread_hole_inner_diameter_max = thread_hole_inner_diameter_max;
        this.thread_hole_inner_diameter_min = thread_hole_inner_diameter_min;
        this.couping_od_max = couping_od_max;
        this.couping_od_min = couping_od_min;
        this.couping_length_max = couping_length_max;
        this.couping_length_min = couping_length_min;
        this.last_update_time = last_update_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThread_acceptance_criteria_no() {
        return thread_acceptance_criteria_no;
    }

    public void setThread_acceptance_criteria_no(String thread_acceptance_criteria_no) {
        this.thread_acceptance_criteria_no = thread_acceptance_criteria_no;
    }

    public float getThread_pitch_diameter_max() {
        return thread_pitch_diameter_max;
    }

    public void setThread_pitch_diameter_max(float thread_pitch_diameter_max) {
        this.thread_pitch_diameter_max = thread_pitch_diameter_max;
    }

    public float getThread_pitch_diameter_min() {
        return thread_pitch_diameter_min;
    }

    public void setThread_pitch_diameter_min(float thread_pitch_diameter_min) {
        this.thread_pitch_diameter_min = thread_pitch_diameter_min;
    }

    public float getThread_sealing_surface_diameter_max() {
        return thread_sealing_surface_diameter_max;
    }

    public void setThread_sealing_surface_diameter_max(float thread_sealing_surface_diameter_max) {
        this.thread_sealing_surface_diameter_max = thread_sealing_surface_diameter_max;
    }

    public float getThread_sealing_surface_diameter_min() {
        return thread_sealing_surface_diameter_min;
    }

    public void setThread_sealing_surface_diameter_min(float thread_sealing_surface_diameter_min) {
        this.thread_sealing_surface_diameter_min = thread_sealing_surface_diameter_min;
    }

    public float getThread_sealing_surface_ovality_max() {
        return thread_sealing_surface_ovality_max;
    }

    public void setThread_sealing_surface_ovality_max(float thread_sealing_surface_ovality_max) {
        this.thread_sealing_surface_ovality_max = thread_sealing_surface_ovality_max;
    }

    public float getThread_sealing_surface_ovality_min() {
        return thread_sealing_surface_ovality_min;
    }

    public void setThread_sealing_surface_ovality_min(float thread_sealing_surface_ovality_min) {
        this.thread_sealing_surface_ovality_min = thread_sealing_surface_ovality_min;
    }

    public float getThread_pitch_max() {
        return thread_pitch_max;
    }

    public void setThread_pitch_max(float thread_pitch_max) {
        this.thread_pitch_max = thread_pitch_max;
    }

    public float getThread_pitch_min() {
        return thread_pitch_min;
    }

    public void setThread_pitch_min(float thread_pitch_min) {
        this.thread_pitch_min = thread_pitch_min;
    }

    public float getThread_taper_max() {
        return thread_taper_max;
    }

    public void setThread_taper_max(float thread_taper_max) {
        this.thread_taper_max = thread_taper_max;
    }

    public float getThread_taper_min() {
        return thread_taper_min;
    }

    public void setThread_taper_min(float thread_taper_min) {
        this.thread_taper_min = thread_taper_min;
    }

    public float getThread_height_max() {
        return thread_height_max;
    }

    public void setThread_height_max(float thread_height_max) {
        this.thread_height_max = thread_height_max;
    }

    public float getThread_height_min() {
        return thread_height_min;
    }

    public void setThread_height_min(float thread_height_min) {
        this.thread_height_min = thread_height_min;
    }

    public float getThread_bearing_surface_width_max() {
        return thread_bearing_surface_width_max;
    }

    public void setThread_bearing_surface_width_max(float thread_bearing_surface_width_max) {
        this.thread_bearing_surface_width_max = thread_bearing_surface_width_max;
    }

    public float getThread_bearing_surface_width_min() {
        return thread_bearing_surface_width_min;
    }

    public void setThread_bearing_surface_width_min(float thread_bearing_surface_width_min) {
        this.thread_bearing_surface_width_min = thread_bearing_surface_width_min;
    }

    public float getCouping_inner_end_depth_max() {
        return couping_inner_end_depth_max;
    }

    public void setCouping_inner_end_depth_max(float couping_inner_end_depth_max) {
        this.couping_inner_end_depth_max = couping_inner_end_depth_max;
    }

    public float getCouping_inner_end_depth_min() {
        return couping_inner_end_depth_min;
    }

    public void setCouping_inner_end_depth_min(float couping_inner_end_depth_min) {
        this.couping_inner_end_depth_min = couping_inner_end_depth_min;
    }

    public float getThread_hole_inner_diameter_max() {
        return thread_hole_inner_diameter_max;
    }

    public void setThread_hole_inner_diameter_max(float thread_hole_inner_diameter_max) {
        this.thread_hole_inner_diameter_max = thread_hole_inner_diameter_max;
    }

    public float getThread_hole_inner_diameter_min() {
        return thread_hole_inner_diameter_min;
    }

    public void setThread_hole_inner_diameter_min(float thread_hole_inner_diameter_min) {
        this.thread_hole_inner_diameter_min = thread_hole_inner_diameter_min;
    }

    public float getCouping_od_max() {
        return couping_od_max;
    }

    public void setCouping_od_max(float couping_od_max) {
        this.couping_od_max = couping_od_max;
    }

    public float getCouping_od_min() {
        return couping_od_min;
    }

    public void setCouping_od_min(float couping_od_min) {
        this.couping_od_min = couping_od_min;
    }

    public float getCouping_length_max() {
        return couping_length_max;
    }

    public void setCouping_length_max(float couping_length_max) {
        this.couping_length_max = couping_length_max;
    }

    public float getCouping_length_min() {
        return couping_length_min;
    }

    public void setCouping_length_min(float couping_length_min) {
        this.couping_length_min = couping_length_min;
    }

    public Date getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(Date last_update_time) {
        this.last_update_time = last_update_time;
    }
}
