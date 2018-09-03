package com.spjzweb.dao;



import com.spjzweb.entity.ThreadInspectionRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface ThreadInspectionRecordDao {

    //分页查询接箍检验记录
    public List<HashMap<String,Object>> getAllByLike(@Param("contract_no") String contract_no, @Param("coupling_no") String coupling_no, @Param("operator_no")String operator_no, @Param("begin_time")Date begin_time, @Param("end_time")Date end_time, @Param("skip") int skip, @Param("take") int take);

    //分页查询接箍检验记录总数
    public int getCountAllByLike(@Param("contract_no") String contract_no, @Param("coupling_no") String coupling_no,@Param("operator_no")String operator_no,@Param("begin_time")Date begin_time, @Param("end_time")Date end_time);

    //修改检验记录
    public int updateThreadInspectionRecord(ThreadInspectionRecord threadInspectionRecord);

    //增加检验记录
    public int addThreadInspectionRecord(ThreadInspectionRecord threadInspectionRecord);

    //删除检验记录
    public int delThreadInspectionRecord(String[] arrId);

    //根据检验记录编号得到检验记录
    public List<HashMap<String,Object>> getThreadInspectionRecordByInspectionRecordCode(@Param("thread_inspection_record_code") String thread_inspection_record_code);

    //客户端获取数据(winform使用)
    public List<ThreadInspectionRecord>getThreadInspectionRecordOfWinform(@Param("operator_no")String operator_no,@Param("production_crew")String production_crew,@Param("production_shift")String production_shift,@Param("contract_no")String contract_no,@Param("threading_type")String threading_type,@Param("od")String od,@Param("wt")String wt,@Param("pipe_heat_no")String pipe_heat_no,@Param("pipe_lot_no")String pipe_lot_no,@Param("beginTime")Date beginTime,@Param("endTime")Date endTime);

    //根据检验记录编号查询检验记录(winform使用)
    public ThreadInspectionRecord getThreadInspectionRecordByNo(@Param("thread_inspection_record_code") String thread_inspection_record_code);

    //修改检验记录(winform使用)
    public int updateThreadInspectionRecordByCode(ThreadInspectionRecord threadInspectionRecord);

    //删除检验记录(winform使用)
    public int delThreadInspectionRecordOfWinform(@Param("thread_inspection_record_code") String thread_inspection_record_code);

}
