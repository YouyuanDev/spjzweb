package com.spjzweb.dao;



import com.spjzweb.entity.ThreadInspectionRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface ThreadInspectionRecordDao {

    //模糊搜索带分页
    public List<HashMap<String,Object>> getAllByLike(@Param("contract_no") String contract_no, @Param("coupling_no") String coupling_no, @Param("operator_no")String operator_no, @Param("begin_time")Date begin_time, @Param("end_time")Date end_time, @Param("skip") int skip, @Param("take") int take);

    //模糊搜索总数
    public int getCountAllByLike(@Param("contract_no") String contract_no, @Param("coupling_no") String coupling_no,@Param("operator_no")String operator_no,@Param("begin_time")Date begin_time, @Param("end_time")Date end_time);

    //修改ItemRecord
    public int updateThreadInspectionRecord(ThreadInspectionRecord threadInspectionRecord);
    //增加ItemRecord
    public int addThreadInspectionRecord(ThreadInspectionRecord threadInspectionRecord);
    //删除ItemRecord
    public int delThreadInspectionRecord(String[] arrId);
    //根据 thread_inspection_record_code 得到 ThreadInspectionRecord 集合
    public List<HashMap<String,Object>> getThreadInspectionRecordByInspectionRecordCode(@Param("thread_inspection_record_code") String thread_inspection_record_code);
    //客户端获取数据(winform)
    public List<ThreadInspectionRecord>getThreadInspectionRecordOfWinform(@Param("od")float od,@Param("wt")float wt,@Param("threading_type")String threading_type,@Param("thread_acceptance_criteria_no")String thread_acceptance_criteria_no);
    //根据检验记录编号查询检验记录(winform)
    public ThreadInspectionRecord getThreadInspectionRecordByNo(@Param("thread_inspection_record_code") String thread_inspection_record_code);
    //修改检验记录(winform)
    public int updateThreadInspectionRecordByCode(ThreadInspectionRecord threadInspectionRecord);

}
