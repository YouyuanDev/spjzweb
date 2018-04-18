package com.spjzweb.dao;

import com.spjzweb.entity.ThreadingAcceptanceCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ThreadingAcceptanceCriteriaDao {


        //模糊搜索带分页
        public List<ThreadingAcceptanceCriteria> getAllByLike(@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no, @Param("skip") int skip, @Param("take") int take);

        //模糊搜索总数
        public int getCountAllByLike(@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);

        //修改ItemRecord
        public int updateThreadingAcceptanceCriteria(ThreadingAcceptanceCriteria threadingAcceptanceCriteria);
        //增加ItemRecord
        public int addThreadingAcceptanceCriteria(ThreadingAcceptanceCriteria threadingAcceptanceCriteria);
        //删除ItemRecord
        public int delThreadingAcceptanceCriteria(String[] arrId);

        //根据 thread_acceptance_criteria_no 得到 ThreadingAcceptanceCriteria 集合
        public List<HashMap<String,Object>> getThreadingAcceptanceCriteriaByAcceptanceCriteriaNo(@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);

        public List<ThreadingAcceptanceCriteria> getAllDropDown();
}
