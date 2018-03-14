package com.spjzweb.dao;

import com.spjzweb.entity.ThreadingProcess;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface ThreadingProcessDao {

    public int addThreadingProcess(ThreadingProcess threadingProcess);
    public int delThreadingProcess(String[] arrId);
    public int updateThreadingProcess(ThreadingProcess threadingProcess);
    public List<HashMap<String,Object>> getAllByLike(@Param("couping_no") String couping_no, @Param("operator_no") String operator_no, @Param("begin_time") Date begin_time, @Param("end_time") Date end_time,@Param("skip") int skip, @Param("take") int take);
    public int getCountAllByLike(@Param("couping_no") String couping_no, @Param("operator_no") String operator_no, @Param("begin_time") Date begin_time, @Param("end_time") Date end_time);
    public List<ThreadingProcess>getThreadingProcess(@Param("couping_no") String couping_no, @Param("operator_no") String operator_no, @Param("begin_time") Date begin_time, @Param("end_time") Date end_time,@Param("skip") int skip, @Param("take") int take);
    public int getCount(@Param("couping_no") String couping_no, @Param("operator_no") String operator_no, @Param("begin_time") Date begin_time, @Param("end_time") Date end_time);
}
