package com.spjzweb.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spjzweb.dao.ItemRecordDao;
import com.spjzweb.entity.DynamicMeasurementItem;
import com.spjzweb.entity.ItemRecord;
import com.spjzweb.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ItemRecordOperation")
public class ItemRecordController {
    @Autowired
    private ItemRecordDao itemRecordDao;
    //根据螺纹记录编号查询测量项值
    @RequestMapping(value = "getItemRecordByInspectionNo",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getItemRecordByInspectionNo(HttpServletRequest request){
        String inspection_record_no=request.getParameter("thread_inspection_record_code");
        List<ItemRecord> list=new ArrayList<>();
        String mmp= "";
        if(inspection_record_no!=null&&!inspection_record_no.equals("")){
            list=itemRecordDao.getItemRecordByInspectionRecordCode(inspection_record_no);
            Map<String,Object> maps=new HashMap<String,Object>();
            mmp= JSONArray.toJSONString(list);
        }
        return mmp;
    }
    //保存function
    @RequestMapping(value = "/saveItemRecord")
    @ResponseBody
    public String saveItemRecord(HttpServletRequest request,HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            int resTotal=0;
            //先判断是否存在相通的合同编号
            String id=request.getParameter("id");
            String itemcode=request.getParameter("itemcode");
            String thread_inspection_record_code=request.getParameter("thread_inspection_record_code");
            String itemvalue=request.getParameter("itemvalue");
            String toolcode1=request.getParameter("toolcode1");
            String toolcode2=request.getParameter("toolcode2");
            String measure_sample1=request.getParameter("measure_sample1");
            String measure_sample2=request.getParameter("measure_sample2");
            if((itemcode!=null&&!itemcode.equals(""))&&(thread_inspection_record_code!=null&&!thread_inspection_record_code.equals(""))){
                ItemRecord item=new ItemRecord();
                if(id!=null&&!id.equals("")){
                    //修改
                    List<ItemRecord>InfoList=itemRecordDao.getItemRecordByItemCodeOfEdit(itemcode,thread_inspection_record_code,Integer.parseInt(id));
                    if (InfoList!=null&&InfoList.size()>0){
                        //新增的时候存在相通标准的编码
                        json.put("promptkey","ishave");
                        json.put("promptValue","该测量项编码已存在!");
                    }else{
                        item.setId(Integer.parseInt(id));
                        if(itemcode!=null&&!itemcode.equals(""))
                            item.setItemcode(itemcode);
                        if(itemvalue!=null&&!itemvalue.equals(""))
                            item.setItemvalue(itemvalue);
                        if(toolcode1!=null&&!toolcode1.equals(""))
                            item.setToolcode1(toolcode1);
                        if(toolcode2!=null&&!toolcode2.equals(""))
                            item.setToolcode2(toolcode2);
                        if(measure_sample1!=null&&!measure_sample1.equals(""))
                            item.setMeasure_sample1(measure_sample1);
                        if(measure_sample2!=null&&!measure_sample2.equals(""))
                            item.setMeasure_sample2(measure_sample2);
                        resTotal=itemRecordDao.updateItemRecord(item);
                        if(resTotal>0){
                            json.put("promptkey","success");
                        }else{
                            json.put("promptkey","fail2");
                            json.put("promptValue","保存失败");
                        }
                    }
                }else{
                    //新增
                    List<ItemRecord>InfoList=itemRecordDao.getItemRecordCodeOfAdd(itemcode,thread_inspection_record_code);
                    if (InfoList!=null&&InfoList.size()>0){
                        //新增的时候存在相通标准的编码
                        json.put("promptkey","ishave");
                        json.put("promptValue","该测量项编码已存在!");
                    }else{
                        item.setItemcode(itemcode);
                        item.setThread_inspection_record_code(thread_inspection_record_code);
                        if(itemvalue!=null&&!itemvalue.equals(""))
                            item.setItemvalue(itemvalue);
                        if(toolcode1!=null&&!toolcode1.equals(""))
                            item.setToolcode1(toolcode1);
                        if(toolcode2!=null&&!toolcode2.equals(""))
                            item.setToolcode2(toolcode2);
                        if(measure_sample1!=null&&!measure_sample1.equals(""))
                            item.setMeasure_sample1(measure_sample1);
                        if(measure_sample2!=null&&!measure_sample2.equals(""))
                            item.setMeasure_sample2(measure_sample2);
                        resTotal=itemRecordDao.addItemRecord(item);
                        //接收id的值
                        if(resTotal>0){
                            json.put("promptkey","success");
                            json.put("promptValue",item.getId());
                        }else{
                            json.put("promptkey","fail2");
                            json.put("promptValue","保存失败");
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            json.put("promptkey","error");
            json.put("promptValue",e.getMessage());

        }finally {
            try {
                ResponseUtil.write(response, json);
            }catch  (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @RequestMapping("/delItemRecord")
    @ResponseBody
    public String delItemRecord(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JSONObject json=new JSONObject();
        try{
            String hlparam=request.getParameter("hlparam");
            StringBuilder sbmessage = new StringBuilder();
            if(hlparam!=null){
                String[]idArr=hlparam.split(",");
                int resTotal=0;
                resTotal=itemRecordDao.delItemRecord(idArr);
                if(resTotal>0){
                    json.put("promptkey","success");
                }else{
                    json.put("promptkey","fail");
                }
            }else{
                json.put("promptkey","fail");
            }
        }catch (Exception e){
            json.put("promptkey","fail");
        }
        ResponseUtil.write(response,json);
        return null;
    }
}
