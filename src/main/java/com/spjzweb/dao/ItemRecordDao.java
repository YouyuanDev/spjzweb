package com.spjzweb.dao;


import com.spjzweb.entity.ItemRecord;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ItemRecordDao {


    //模糊搜索带分页
    public List<HashMap<String,Object>> getAllByLike(@Param("itemcode") String itemcode, @Param("thread_inspection_record_code") String thread_inspection_record_code, @Param("skip") int skip, @Param("take") int take);

    //模糊搜索总数
    public int getCountAllByLike(@Param("itemcode") String itemcode, @Param("thread_inspection_record_code") String thread_inspection_record_code);

    //修改ItemRecord
    public int updateItemRecord(ItemRecord itemRecord);
    //增加ItemRecord
    public int addItemRecord(ItemRecord itemRecord);
    //删除ItemRecord
    public int delItemRecord(String[] arrId);

    //根据thread_inspection_record_code 得到ItemRecord 集合
    public List<ItemRecord> getItemRecordByInspectionRecordCode(@Param("thread_inspection_record_code") String thread_inspection_record_code);

    //根据thread_inspection_record_code 得到ItemRecord 集合
//    public List<HashMap<String,Object>> getItemRecordByInspectionNo(@Param("thread_inspection_record_code") String thread_inspection_record_code);
    //修改时判断编号是否存在
    public List<ItemRecord>getItemRecordByItemCodeOfEdit(@Param("itemcode") String itemcode,@Param("thread_inspection_record_code") String thread_inspection_record_code,@Param("id") int id);
    //添加时判断编号是否存在
    public List<ItemRecord>getItemRecordByItemCodeOfAdd(@Param("itemcode") String itemcode,@Param("thread_inspection_record_code") String thread_inspection_record_code);
}

