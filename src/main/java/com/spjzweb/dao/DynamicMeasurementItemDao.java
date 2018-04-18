package com.spjzweb.dao;

import com.spjzweb.entity.DynamicMeasurementItem;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;



public interface DynamicMeasurementItemDao {


    //模糊搜索带分页
    public List<HashMap<String,Object>> getAllByLike(@Param("measure_item_code") String measure_item_code,@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no, @Param("skip") int skip, @Param("take") int take);

    //模糊搜索总数
    public int getCountAllByLike(@Param("measure_item_code") String measure_item_code, @Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);

    //修改ItemRecord
    public int updateDynamicMeasurementItem(DynamicMeasurementItem dynamicMeasurementItem);
    //增加ItemRecord
    public int addDynamicMeasurementItem(DynamicMeasurementItem dynamicMeasurementItem);
    //删除ItemRecord
    public int delDynamicMeasurementItem(String[] arrId);

    //根据 thread_acceptance_criteria_no 得到 DynamicMeasurementItem 集合
    public List<DynamicMeasurementItem> getDynamicMeasurementItemByAcceptanceCriteriaNo(@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);

    //根据measure_item_code和thread_acceptance_criteria_no号查询是否存在
    public List<DynamicMeasurementItem>getDataByItemCodeAndAcceptanceCriteriaNo(@Param("measure_item_code") String measure_item_code,@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);
    //根据thread_acceptance_criteria_no查询静态多有检测项编码
    public List<HashMap<String,Object>>getAllDropDownStaticMeasureItem(@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);
}

