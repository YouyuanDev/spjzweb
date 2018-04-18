package com.spjzweb.entity;

import java.util.Date;

public class ThreadingAcceptanceCriteria {

    private int id;
    private String thread_acceptance_criteria_no;
    private Date last_update_time;

    public ThreadingAcceptanceCriteria() {
    }

    public ThreadingAcceptanceCriteria(int id, String thread_acceptance_criteria_no, Date last_update_time) {
        this.id = id;
        this.thread_acceptance_criteria_no = thread_acceptance_criteria_no;
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

    public Date getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(Date last_update_time) {
        this.last_update_time = last_update_time;
    }
}
