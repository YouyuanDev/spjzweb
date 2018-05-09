package com.spjzweb.entity;

import java.util.Date;

public class ThreadInspectionRecord {


    private int id;
    private String thread_inspection_record_code;
    private String coupling_no;
    private String contract_no;
    private String production_line;
    private String machine_no;
    private String process_no;
    private String operator_no;
    private String production_crew;
    private String production_shift;
    private String video_no;

    private Date inspection_time;
    private String inspection_result;
    private String coupling_heat_no;
    private String coupling_lot_no;

    public ThreadInspectionRecord() {
    }

    public ThreadInspectionRecord(int id, String thread_inspection_record_code, String coupling_no, String contract_no, String production_line, String machine_no, String process_no, String operator_no, String production_crew, String production_shift, String video_no, Date inspection_time, String inspection_result, String coupling_heat_no, String coupling_lot_no) {
        this.id = id;
        this.thread_inspection_record_code = thread_inspection_record_code;
        this.coupling_no = coupling_no;
        this.contract_no = contract_no;
        this.production_line = production_line;
        this.machine_no = machine_no;
        this.process_no = process_no;
        this.operator_no = operator_no;
        this.production_crew = production_crew;
        this.production_shift = production_shift;
        this.video_no = video_no;
        this.inspection_time = inspection_time;
        this.inspection_result = inspection_result;
        this.coupling_heat_no = coupling_heat_no;
        this.coupling_lot_no = coupling_lot_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThread_inspection_record_code() {
        return thread_inspection_record_code;
    }

    public void setThread_inspection_record_code(String thread_inspection_record_code) {
        this.thread_inspection_record_code = thread_inspection_record_code;
    }

    public String getCoupling_no() {
        return coupling_no;
    }

    public void setCoupling_no(String coupling_no) {
        this.coupling_no = coupling_no;
    }

    public String getContract_no() {
        return contract_no;
    }

    public void setContract_no(String contract_no) {
        this.contract_no = contract_no;
    }

    public String getProduction_line() {
        return production_line;
    }

    public void setProduction_line(String production_line) {
        this.production_line = production_line;
    }

    public String getMachine_no() {
        return machine_no;
    }

    public void setMachine_no(String machine_no) {
        this.machine_no = machine_no;
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

    public String getProduction_crew() {
        return production_crew;
    }

    public void setProduction_crew(String production_crew) {
        this.production_crew = production_crew;
    }

    public String getProduction_shift() {
        return production_shift;
    }

    public void setProduction_shift(String production_shift) {
        this.production_shift = production_shift;
    }

    public String getVideo_no() {
        return video_no;
    }

    public void setVideo_no(String video_no) {
        this.video_no = video_no;
    }

    public Date getInspection_time() {
        return inspection_time;
    }

    public void setInspection_time(Date inspection_time) {
        this.inspection_time = inspection_time;
    }

    public String getInspection_result() {
        return inspection_result;
    }

    public void setInspection_result(String inspection_result) {
        this.inspection_result = inspection_result;
    }

    public String getCoupling_heat_no() {
        return coupling_heat_no;
    }

    public void setCoupling_heat_no(String coupling_heat_no) {
        this.coupling_heat_no = coupling_heat_no;
    }

    public String getCoupling_lot_no() {
        return coupling_lot_no;
    }

    public void setCoupling_lot_no(String coupling_lot_no) {
        this.coupling_lot_no = coupling_lot_no;
    }
}
