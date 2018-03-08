package com.spjzweb.entity;

import java.util.Date;

public class ToolMeasuringRecord {
    private int id;
    private String tool_measuring_record_no;
    private String thread_pitch_gauge_no;
    private String thread_pitch_calibration_framwork;
    private String sealing_surface_gauge_no;
    private String sealing_surface_calibration_ring_no;
    private String depth_caliper_no;
    private String threading_distance_gauge_no;
    private String thread_distance_calibration_sample_no;
    private String taper_gauge_no;
    private String tooth_height_gauge_no;
    private String tooth_height_calibration_sample_no;
    private String tooth_width_stop_gauge_no;
    private String thread_min_length_sample_no;
    private String coupling_length_sample_no;
    private String caliper_no;
    private String caliper_tolerance;
    private String collar_gauge_no;
    public ToolMeasuringRecord() {
    }

    public ToolMeasuringRecord(int id, String tool_measuring_record_no, String thread_pitch_gauge_no, String thread_pitch_calibration_framwork, String sealing_surface_gauge_no, String sealing_surface_calibration_ring_no, String depth_caliper_no, String threading_distance_gauge_no, String thread_distance_calibration_sample_no, String taper_gauge_no, String tooth_height_gauge_no, String tooth_height_calibration_sample_no, String tooth_width_stop_gauge_no, String thread_min_length_sample_no, String coupling_length_sample_no, String caliper_no, String caliper_tolerance, String collar_gauge_no) {
        this.id = id;
        this.tool_measuring_record_no = tool_measuring_record_no;
        this.thread_pitch_gauge_no = thread_pitch_gauge_no;
        this.thread_pitch_calibration_framwork = thread_pitch_calibration_framwork;
        this.sealing_surface_gauge_no = sealing_surface_gauge_no;
        this.sealing_surface_calibration_ring_no = sealing_surface_calibration_ring_no;
        this.depth_caliper_no = depth_caliper_no;
        this.threading_distance_gauge_no = threading_distance_gauge_no;
        this.thread_distance_calibration_sample_no = thread_distance_calibration_sample_no;
        this.taper_gauge_no = taper_gauge_no;
        this.tooth_height_gauge_no = tooth_height_gauge_no;
        this.tooth_height_calibration_sample_no = tooth_height_calibration_sample_no;
        this.tooth_width_stop_gauge_no = tooth_width_stop_gauge_no;
        this.thread_min_length_sample_no = thread_min_length_sample_no;
        this.coupling_length_sample_no = coupling_length_sample_no;
        this.caliper_no = caliper_no;
        this.caliper_tolerance = caliper_tolerance;
        this.collar_gauge_no = collar_gauge_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTool_measuring_record_no() {
        return tool_measuring_record_no;
    }

    public void setTool_measuring_record_no(String tool_measuring_record_no) {
        this.tool_measuring_record_no = tool_measuring_record_no;
    }

    public String getThread_pitch_gauge_no() {
        return thread_pitch_gauge_no;
    }

    public void setThread_pitch_gauge_no(String thread_pitch_gauge_no) {
        this.thread_pitch_gauge_no = thread_pitch_gauge_no;
    }

    public String getThread_pitch_calibration_framwork() {
        return thread_pitch_calibration_framwork;
    }

    public void setThread_pitch_calibration_framwork(String thread_pitch_calibration_framwork) {
        this.thread_pitch_calibration_framwork = thread_pitch_calibration_framwork;
    }

    public String getSealing_surface_gauge_no() {
        return sealing_surface_gauge_no;
    }

    public void setSealing_surface_gauge_no(String sealing_surface_gauge_no) {
        this.sealing_surface_gauge_no = sealing_surface_gauge_no;
    }

    public String getSealing_surface_calibration_ring_no() {
        return sealing_surface_calibration_ring_no;
    }

    public void setSealing_surface_calibration_ring_no(String sealing_surface_calibration_ring_no) {
        this.sealing_surface_calibration_ring_no = sealing_surface_calibration_ring_no;
    }

    public String getDepth_caliper_no() {
        return depth_caliper_no;
    }

    public void setDepth_caliper_no(String depth_caliper_no) {
        this.depth_caliper_no = depth_caliper_no;
    }

    public String getThreading_distance_gauge_no() {
        return threading_distance_gauge_no;
    }

    public void setThreading_distance_gauge_no(String threading_distance_gauge_no) {
        this.threading_distance_gauge_no = threading_distance_gauge_no;
    }

    public String getThread_distance_calibration_sample_no() {
        return thread_distance_calibration_sample_no;
    }

    public void setThread_distance_calibration_sample_no(String thread_distance_calibration_sample_no) {
        this.thread_distance_calibration_sample_no = thread_distance_calibration_sample_no;
    }

    public String getTaper_gauge_no() {
        return taper_gauge_no;
    }

    public void setTaper_gauge_no(String taper_gauge_no) {
        this.taper_gauge_no = taper_gauge_no;
    }

    public String getTooth_height_gauge_no() {
        return tooth_height_gauge_no;
    }

    public void setTooth_height_gauge_no(String tooth_height_gauge_no) {
        this.tooth_height_gauge_no = tooth_height_gauge_no;
    }

    public String getTooth_height_calibration_sample_no() {
        return tooth_height_calibration_sample_no;
    }

    public void setTooth_height_calibration_sample_no(String tooth_height_calibration_sample_no) {
        this.tooth_height_calibration_sample_no = tooth_height_calibration_sample_no;
    }

    public String getTooth_width_stop_gauge_no() {
        return tooth_width_stop_gauge_no;
    }

    public void setTooth_width_stop_gauge_no(String tooth_width_stop_gauge_no) {
        this.tooth_width_stop_gauge_no = tooth_width_stop_gauge_no;
    }

    public String getThread_min_length_sample_no() {
        return thread_min_length_sample_no;
    }

    public void setThread_min_length_sample_no(String thread_min_length_sample_no) {
        this.thread_min_length_sample_no = thread_min_length_sample_no;
    }

    public String getCoupling_length_sample_no() {
        return coupling_length_sample_no;
    }

    public void setCoupling_length_sample_no(String coupling_length_sample_no) {
        this.coupling_length_sample_no = coupling_length_sample_no;
    }

    public String getCaliper_no() {
        return caliper_no;
    }

    public void setCaliper_no(String caliper_no) {
        this.caliper_no = caliper_no;
    }

    public String getCaliper_tolerance() {
        return caliper_tolerance;
    }

    public void setCaliper_tolerance(String caliper_tolerance) {
        this.caliper_tolerance = caliper_tolerance;
    }

    public String getCollar_gauge_no() {
        return collar_gauge_no;
    }

    public void setCollar_gauge_no(String collar_gauge_no) {
        this.collar_gauge_no = collar_gauge_no;
    }
}
