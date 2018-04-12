package com.spjzweb.dao;

import com.spjzweb.entity.ThreadAcceptanceCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface ThreadAcceptanceCriteriaDao {

    public int addThreadAcceptanceCriteria(ThreadAcceptanceCriteria threadAcceptanceCriteria);
    public int delThreadAcceptanceCriteria(String[] arrId);
    public int updateThreadAcceptanceCriteria(ThreadAcceptanceCriteria threadAcceptanceCriteria);
    public List<ThreadAcceptanceCriteria> getAllByLike(@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no, @Param("skip") int skip, @Param("take") int take);
    public int getCountAllByLike(@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);
    public List<ThreadAcceptanceCriteria>getAllDropDownAcceptanceCriteria(@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);
}
