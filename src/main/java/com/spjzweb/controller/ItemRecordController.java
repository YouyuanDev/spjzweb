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
import java.io.BufferedReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ItemRecordOperation")
public class ItemRecordController {
    @Autowired
    private ItemRecordDao itemRecordDao;
    /**
     * 根据螺纹检验记录编号查询测量项值
     * @param request
     * @return
     */
    @RequestMapping(value = "getItemRecordByInspectionNo",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getItemRecordByInspectionNo(HttpServletRequest request){
        String inspection_record_no=request.getParameter("thread_inspection_record_code");
        List<HashMap<String,Object>> list=new ArrayList<>();
        String mmp= "";
        if(inspection_record_no!=null&&!inspection_record_no.equals("")){
            list=itemRecordDao.getItemRecordByInspectionRecordCode(inspection_record_no);
            mmp= JSONArray.toJSONString(list);
        }
        return mmp;
    }
    /**
     * 添加和修改螺纹测量项
     * @param request
     * @param response
     * @return
     */
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
            String reading_max=request.getParameter("reading_max");
            String reading_min=request.getParameter("reading_min");
            String reading_avg=request.getParameter("reading_avg");
            String reading_ovality=request.getParameter("reading_ovality");
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
                        item.setThread_inspection_record_code(thread_inspection_record_code);
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
                        if(reading_max!=null&&!reading_max.equals(""))
                            item.setReading_max(reading_max);
                        if(reading_min!=null&&!reading_min.equals(""))
                            item.setReading_min(reading_min);
                        if(reading_avg!=null&&!reading_avg.equals(""))
                            item.setReading_avg(reading_avg);
                        if(reading_ovality!=null&&!reading_ovality.equals(""))
                            item.setReading_ovality(reading_ovality);
                        resTotal=itemRecordDao.updateItemRecord(item);

                    }
                }else{
                    //新增
                    List<ItemRecord>InfoList=itemRecordDao.getItemRecordByItemCodeOfAdd(itemcode,thread_inspection_record_code);
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
                        if(reading_max!=null&&!reading_max.equals(""))
                            item.setReading_max(reading_max);
                        if(reading_min!=null&&!reading_min.equals(""))
                            item.setReading_min(reading_min);
                        if(reading_avg!=null&&!reading_avg.equals(""))
                            item.setReading_avg(reading_avg);
                        if(reading_ovality!=null&&!reading_ovality.equals(""))
                            item.setReading_ovality(reading_ovality);
                        resTotal=itemRecordDao.addItemRecord(item);
                    }
                }
            }
            if(resTotal>0){
                json.put("promptkey","success");
                json.put("promptValue","legal");
            }else{
                json.put("promptkey","fail2");
                json.put("promptValue","保存失败");
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

    /**
     * 删除螺纹测量项
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
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
    /**
     * 根据检测记录编号查询测量项数据(客户端使用)
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getItemRecordByInspectionNoOfWinform")
    @ResponseBody
    public String getItemRecordByInspectionNoOfWinform(HttpServletRequest request,HttpServletResponse response){
        JSONObject jsonReturn=new JSONObject();
        try{
            //读取传过来的检验记录编号
            StringBuilder sb=new StringBuilder();
            BufferedReader reader=request.getReader();
            String input=null;
            while ((input=reader.readLine())!=null){
                sb.append(input);
            }
            JSONObject json=JSONObject.parseObject(sb.toString());
            String thread_inspection_record_code=null;
            if(json!=null){
                thread_inspection_record_code=json.getString("thread_inspection_record_code");
            }
            List<HashMap<String,Object>>list=itemRecordDao.getItemRecordByInspectionNoOfWinform(thread_inspection_record_code);
            jsonReturn.put("rowsData",list);
            ResponseUtil.write(response,jsonReturn);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            ResponseUtil.write(response,jsonReturn);
        }catch (Exception e){

        }
        return null;
    }
}
