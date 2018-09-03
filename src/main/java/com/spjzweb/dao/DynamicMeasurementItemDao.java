package com.spjzweb.dao;

import com.spjzweb.entity.DynamicMeasurementItem;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;



public interface DynamicMeasurementItemDao {

    //根据就收标准的编号查询所有的动态测量项信息
    public List<HashMap<String,Object>>getDynamicMeasureItemByAcceptanceNo(@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);

    //分页查询获取动态测量项
    public List<HashMap<String,Object>> getAllByLike(@Param("measure_item_code") String measure_item_code,@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no, @Param("skip") int skip, @Param("take") int take);

    //获取动态测量项总数
    public int getCountAllByLike(@Param("measure_item_code") String measure_item_code, @Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);

    //修改动态测量项
    public int updateDynamicMeasurementItem(DynamicMeasurementItem dynamicMeasurementItem);

    //增加动态测量项
    public int addDynamicMeasurementItem(DynamicMeasurementItem dynamicMeasurementItem);

    //删除动态测量项
    public int delDynamicMeasurementItem(String[] arrId);

    //根据接收标准编号获取动态测量项集合
    public List<DynamicMeasurementItem> getDynamicMeasurementItemByAcceptanceCriteriaNo(@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);

    //根据测量项编码和接收标准编号查询动态测量项是否存在
    public List<DynamicMeasurementItem>getDataByItemCodeAndAcceptanceCriteriaNo(@Param("measure_item_code") String measure_item_code,@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);

    //根据接收标准编号查询静态静态测量项集合
    public List<HashMap<String,Object>>getAllDropDownStaticMeasureItem(@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);

    //修改时判断编号是否存在
    public List<DynamicMeasurementItem>getDynamicMeasurementItemByItemCodeOfEdit(@Param("measure_item_code") String measure_item_code,@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no,@Param("id") int id);

    //添加时判断编号是否存在
    public List<DynamicMeasurementItem>getDynamicMeasurementItemByItemCodeOfAdd(@Param("measure_item_code") String measure_item_code,@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);

    //根据螺纹记录编号查询下拉测量项值
    public List<HashMap<String,Object>> getAllDropdownItemRecordByInspectionNo(@Param("thread_inspection_record_code") String thread_inspection_record_code);



}

