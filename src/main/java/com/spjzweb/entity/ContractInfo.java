package com.spjzweb.entity;

public class ContractInfo {


    private int id;
    private String contract_no;
    private String machining_contract_no;
    private float od;
    private float wt;
    private String pipe_heat_no;
    private String pipe_lot_no;
    private String pipe_steel_grade;
    private String pipe_meterial;
    private String graph_no;
    private String handbook_no;
    private String seal_sample_graph_no;
    private String thread_sample_graph_no;
    private String thread_acceptance_criteria_no;
    private String remark;
    private String customer_spec;
    private String threading_type;
    private String coupling_type;


    public ContractInfo() {
    }

    public ContractInfo(int id, String contract_no, String machining_contract_no, float od, float wt, String pipe_heat_no, String pipe_lot_no, String pipe_steel_grade, String pipe_meterial, String graph_no, String handbook_no, String seal_sample_graph_no, String thread_sample_graph_no, String thread_acceptance_criteria_no, String remark, String customer_spec, String threading_type, String coupling_type) {
        this.id = id;
        this.contract_no = contract_no;
        this.machining_contract_no = machining_contract_no;
        this.od = od;
        this.wt = wt;
        this.pipe_heat_no = pipe_heat_no;
        this.pipe_lot_no = pipe_lot_no;
        this.pipe_steel_grade = pipe_steel_grade;
        this.pipe_meterial = pipe_meterial;
        this.graph_no = graph_no;
        this.handbook_no = handbook_no;
        this.seal_sample_graph_no = seal_sample_graph_no;
        this.thread_sample_graph_no = thread_sample_graph_no;
        this.thread_acceptance_criteria_no = thread_acceptance_criteria_no;
        this.remark = remark;
        this.customer_spec = customer_spec;
        this.threading_type = threading_type;
        this.coupling_type = coupling_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContract_no() {
        return contract_no;
    }

    public void setContract_no(String contract_no) {
        this.contract_no = contract_no;
    }

    public String getMachining_contract_no() {
        return machining_contract_no;
    }

    public void setMachining_contract_no(String machining_contract_no) {
        this.machining_contract_no = machining_contract_no;
    }

    public float getOd() {
        return od;
    }

    public void setOd(float od) {
        this.od = od;
    }

    public float getWt() {
        return wt;
    }

    public void setWt(float wt) {
        this.wt = wt;
    }

    public String getPipe_heat_no() {
        return pipe_heat_no;
    }

    public void setPipe_heat_no(String pipe_heat_no) {
        this.pipe_heat_no = pipe_heat_no;
    }

    public String getPipe_lot_no() {
        return pipe_lot_no;
    }

    public void setPipe_lot_no(String pipe_lot_no) {
        this.pipe_lot_no = pipe_lot_no;
    }

    public String getPipe_steel_grade() {
        return pipe_steel_grade;
    }

    public void setPipe_steel_grade(String pipe_steel_grade) {
        this.pipe_steel_grade = pipe_steel_grade;
    }

    public String getPipe_meterial() {
        return pipe_meterial;
    }

    public void setPipe_meterial(String pipe_meterial) {
        this.pipe_meterial = pipe_meterial;
    }

    public String getGraph_no() {
        return graph_no;
    }

    public void setGraph_no(String graph_no) {
        this.graph_no = graph_no;
    }

    public String getHandbook_no() {
        return handbook_no;
    }

    public void setHandbook_no(String handbook_no) {
        this.handbook_no = handbook_no;
    }

    public String getSeal_sample_graph_no() {
        return seal_sample_graph_no;
    }

    public void setSeal_sample_graph_no(String seal_sample_graph_no) {
        this.seal_sample_graph_no = seal_sample_graph_no;
    }

    public String getThread_sample_graph_no() {
        return thread_sample_graph_no;
    }

    public void setThread_sample_graph_no(String thread_sample_graph_no) {
        this.thread_sample_graph_no = thread_sample_graph_no;
    }

    public String getThread_acceptance_criteria_no() {
        return thread_acceptance_criteria_no;
    }

    public void setThread_acceptance_criteria_no(String thread_acceptance_criteria_no) {
        this.thread_acceptance_criteria_no = thread_acceptance_criteria_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCustomer_spec() {
        return customer_spec;
    }

    public void setCustomer_spec(String customer_spec) {
        this.customer_spec = customer_spec;
    }

    public String getThreading_type() {
        return threading_type;
    }

    public void setThreading_type(String threading_type) {
        this.threading_type = threading_type;
    }

    public String getCoupling_type() {
        return coupling_type;
    }

    public void setCoupling_type(String coupling_type) {
        this.coupling_type = coupling_type;
    }
}
