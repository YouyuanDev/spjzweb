package com.spjzweb.entity;

import java.util.Date;

public class ThreadingAcceptanceCriteria {

    private int id;
    private String thread_acceptance_criteria_no;
    private Date last_update_time;

    private String od;
    private String wt;
    private String customer_spec;
    private String coupling_type;
    private String threading_type;
    private String remark;


    public ThreadingAcceptanceCriteria() {
    }

    public ThreadingAcceptanceCriteria(int id, String thread_acceptance_criteria_no, Date last_update_time, String od, String wt, String customer_spec, String coupling_type, String threading_type, String remark) {
        this.id = id;
        this.thread_acceptance_criteria_no = thread_acceptance_criteria_no;
        this.last_update_time = last_update_time;
        this.od = od;
        this.wt = wt;
        this.customer_spec = customer_spec;
        this.coupling_type = coupling_type;
        this.threading_type = threading_type;
        this.remark = remark;
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

    public Date getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(Date last_update_time) {
        this.last_update_time = last_update_time;
    }

    public String getOd() {
        return od;
    }

    public void setOd(String od) {
        this.od = od;
    }

    public String getWt() {
        return wt;
    }

    public void setWt(String wt) {
        this.wt = wt;
    }

    public String getCustomer_spec() {
        return customer_spec;
    }

    public void setCustomer_spec(String customer_spec) {
        this.customer_spec = customer_spec;
    }

    public String getCoupling_type() {
        return coupling_type;
    }

    public void setCoupling_type(String coupling_type) {
        this.coupling_type = coupling_type;
    }

    public String getThreading_type() {
        return threading_type;
    }

    public void setThreading_type(String threading_type) {
        this.threading_type = threading_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
