package com.spjzweb.dao;


import com.spjzweb.entity.ItemRecord;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ItemRecordDao {

    //分页查询测量项信息
    public List<HashMap<String,Object>> getAllByLike(@Param("itemcode") String itemcode, @Param("thread_inspection_record_code") String thread_inspection_record_code, @Param("skip") int skip, @Param("take") int take);

    //分页查询测量项信息总数
    public int getCountAllByLike(@Param("itemcode") String itemcode, @Param("thread_inspection_record_code") String thread_inspection_record_code);

    //修改测量项信息
    public int updateItemRecord(ItemRecord itemRecord);

    //增加测量项信息
    public int addItemRecord(ItemRecord itemRecord);

    //删除测量项信息
    public int delItemRecord(String[] arrId);

    //根据检验记录编号获取测量项集合
    public List<HashMap<String,Object>> getItemRecordByInspectionRecordCode(@Param("thread_inspection_record_code") String thread_inspection_record_code);

    //修改测量项信息时判断编号是否存在
    public List<ItemRecord>getItemRecordByItemCodeOfEdit(@Param("itemcode") String itemcode,@Param("thread_inspection_record_code") String thread_inspection_record_code,@Param("id") int id);

    //添加测量项信息时判断编号是否存在
    public List<ItemRecord>getItemRecordByItemCodeOfAdd(@Param("itemcode") String itemcode,@Param("thread_inspection_record_code") String thread_inspection_record_code);

    //客户端表单提交
    public int addItemRecordByWinform(@Param("list")ArrayList<ItemRecord>itemRecordList);

    //根据检验记录编号查询数据
    public List<HashMap<String,Object>>getItemRecordByInspectionNoOfWinform(@Param("thread_inspection_record_code")String thread_inspection_record_code);

    //客户端更新检验测量记录
    public  int updataItemRecordByCode(@Param("list")ArrayList<ItemRecord> list);

    //客户端更新检验测量记录
    public  int updataItemRecordByCodeSingle(ItemRecord ItemRecord);

    //根据检验记录编号查询检验数据
    public List<HashMap<String,Object>>getInspectionRecordByInspectionNo(@Param("thread_inspection_record_code")String thread_inspection_record_code);

    //客户端删除检验测量记录
    public int delItemRecordByCodeSingle(@Param("thread_inspection_record_code") String thread_inspection_record_code);
}

