package com.spjzweb.dao;

import com.spjzweb.entity.ToolMeasuringRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface ToolMeasuringRecordDao {

    public int addToolMeasuringRecord(ToolMeasuringRecord toolMeasuringRecord);
    public int delToolMeasuringRecord(String[] arrId);
    public int updateToolMeasuringRecord(ToolMeasuringRecord toolMeasuringRecord);
    public List<HashMap<String,Object>> getAllByLike(@Param("tool_measuring_record_no") String tool_measuring_record_no,@Param("skip") int skip, @Param("take") int take);
    public int getCountAllByLike(@Param("tool_measuring_record_no") String tool_measuring_record_no);
    public List<ToolMeasuringRecord> getAllToolRecordByNo(@Param("tool_measuring_record_no") String tool_measuring_record_no);
    public ToolMeasuringRecord getToolRecordByNo(@Param("tool_measuring_record_no") String tool_measuring_record_no);
//    public List<HashMap<String,Object>> getAllByLike(@Param("tool_measuring_record_no") String tool_measuring_record_no,@Param("begin_time") Date begin_time, @Param("end_time") Date end_time, @Param("skip") int skip, @Param("take") int take);
//    public int getCountAllByLike(@Param("tool_measuring_record_no") String tool_measuring_record_no,@Param("begin_time") Date begin_time, @Param("end_time") Date end_time);

}
