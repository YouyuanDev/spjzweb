package com.spjzweb.dao;

import com.spjzweb.entity.StaticMeasurementItem;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface StaticMeasurementItemDao {


    //模糊搜索带分页
    public List<HashMap<String,Object>> getAllByLike(@Param("measure_item_code") String measure_item_code,@Param("measure_item_name") String measure_item_name,@Param("skip") int skip, @Param("take") int take);

    //模糊搜索总数
    public int getCountAllByLike(@Param("measure_item_code") String measure_item_code,@Param("measure_item_name") String measure_item_name);

    //修改ItemRecord
    public int updateStaticMeasurementItem(StaticMeasurementItem staticMeasurementItem);
    //增加ItemRecord
    public int addStaticMeasurementItem(StaticMeasurementItem staticMeasurementItem);
    //删除ItemRecord
    public int delStaticMeasurementItem(String[] arrId);

    //根据 measure_item_code 得到StaticMeasurementItem 集合
    public List<StaticMeasurementItem> getStaticMeasurementItemByItemCodeOfEdit(@Param("measure_item_code") String measure_item_code);
    public List<StaticMeasurementItem> getStaticMeasurementItemByItemCodeOfAdd(@Param("measure_item_code") String measure_item_code);
    //获取下拉框所有的静态测量项
    public List<HashMap<String,Object>>getAllDropdownStaticItem();
    //根据thread_inspection_record_code 得到ItemRecord 集合
   // public List<HashMap<String,Object>> getItemRecordByInspectionNo(@Param("thread_inspection_record_code") String thread_inspection_record_code);
}

