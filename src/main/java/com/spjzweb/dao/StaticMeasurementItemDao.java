package com.spjzweb.dao;

import com.spjzweb.entity.StaticMeasurementItem;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface StaticMeasurementItemDao {


    //分页查询静态测量项
    public List<HashMap<String,Object>> getAllByLike(@Param("measure_item_code") String measure_item_code,@Param("measure_item_name") String measure_item_name,@Param("skip") int skip, @Param("take") int take);

    //分页查询静态测量项总数
    public int getCountAllByLike(@Param("measure_item_code") String measure_item_code,@Param("measure_item_name") String measure_item_name);

    //修改静态测量项
    public int updateStaticMeasurementItem(StaticMeasurementItem staticMeasurementItem);

    //增加静态测量项
    public int addStaticMeasurementItem(StaticMeasurementItem staticMeasurementItem);

    //删除静态测量项
    public int delStaticMeasurementItem(String[] arrId);

    //修改时判断测量项编号是否存在
    public List<StaticMeasurementItem> getStaticMeasurementItemByItemCodeOfEdit(@Param("measure_item_code") String measure_item_code);

    //添加时判断编号是否存在
    public List<StaticMeasurementItem> getStaticMeasurementItemByItemCodeOfAdd(@Param("measure_item_code") String measure_item_code);

    //获取下拉框所有的静态测量项
    public List<HashMap<String,Object>>getAllDropdownStaticItem();

    //根据合同编号获取使用测量工具和标准等信息(Winform使用)
    public List<HashMap<String,Object>>getDynamicAndStaticDataByAcceptanceNo(@Param("thread_acceptance_criteria_no") String thread_acceptance_criteria_no);
}

