package com.spjzweb.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spjzweb.dao.DynamicMeasurementItemDao;
import com.spjzweb.entity.DynamicMeasurementItem;
import com.spjzweb.entity.StaticMeasurementItem;
import com.spjzweb.util.ComboxItem;
import com.spjzweb.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/DynamicMeasure")
public class DynamicMeasurementItemController {
    @Autowired
    private DynamicMeasurementItemDao dynamicMeasurementItemDao;
    @RequestMapping(value = "getDynamicMeasureItemAllByLike",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getDynamicMeasureItemAllByLike(@RequestParam(value = "measure_item_code",required = false)String measure_item_code,@RequestParam(value = "thread_acceptance_criteria_no",required = false)String thread_acceptance_criteria_no,HttpServletRequest request){
        String page= request.getParameter("page");
        String rows= request.getParameter("rows");
        if(page==null){
            page="1";
        }
        if(rows==null){
            rows="20";
        }
        int start=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
        List<HashMap<String,Object>> list=dynamicMeasurementItemDao.getAllByLike(measure_item_code,thread_acceptance_criteria_no,start,Integer.parseInt(rows));
        int count=dynamicMeasurementItemDao.getCountAllByLike(measure_item_code,thread_acceptance_criteria_no);
        Map<String,Object> maps=new HashMap<String,Object>();
        maps.put("total",count);
        maps.put("rows",list);
        String mmp= JSONArray.toJSONString(list);
        return mmp;
    }
    //根据就收标准的编号查询所有的动态测量项信息
    @RequestMapping(value = "getDynamicMeasureItemByAcceptanceNo",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getDynamicMeasureItemByAcceptanceNo(HttpServletRequest request){
        String thread_acceptance_criteria_no=request.getParameter("thread_acceptance_criteria_no");
        List<HashMap<String,Object>> list=new ArrayList<>();
        String mmp= "";
        if(thread_acceptance_criteria_no!=null&&!thread_acceptance_criteria_no.equals("")){
            list=dynamicMeasurementItemDao.getDynamicMeasureItemByAcceptanceNo(thread_acceptance_criteria_no);
            Map<String,Object> maps=new HashMap<String,Object>();
            mmp=JSONArray.toJSONString(list);
        }
        return mmp;
    }

    //保存function
    @RequestMapping(value = "/saveDynamicMeasureItem")
    @ResponseBody
    public String saveDynamicMeasureItem(HttpServletRequest request,HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            int resTotal=0;
            //先判断是否存在相通的合同编号
            String id=request.getParameter("id");
            String measure_item_code=request.getParameter("measure_item_code");
            String thread_acceptance_criteria_no=request.getParameter("thread_acceptance_criteria_no");
            String item_max_value=request.getParameter("item_max_value");
            String item_min_value=request.getParameter("item_min_value");
            String item_frequency=request.getParameter("item_frequency");
            String both_ends=request.getParameter("both_ends");
            String item_std_value=request.getParameter("item_std_value");
            String item_pos_deviation_value=request.getParameter("item_pos_deviation_value");
            String item_neg_deviation_value=request.getParameter("item_neg_deviation_value");
            String reading_types=request.getParameter("reading_types");
            String ovality_max=request.getParameter("ovality_max");
            System.out.println("-----------------------");
            System.out.println(reading_types);
            if((measure_item_code!=null&&!measure_item_code.equals(""))&&(thread_acceptance_criteria_no!=null&&!thread_acceptance_criteria_no.equals(""))){
                DynamicMeasurementItem item=new DynamicMeasurementItem();
                if(id!=null&&!id.equals("")){
                    //修改
                    List<DynamicMeasurementItem>InfoList=dynamicMeasurementItemDao.getDynamicMeasurementItemByItemCodeOfEdit(measure_item_code,thread_acceptance_criteria_no,Integer.parseInt(id));
                    if (InfoList!=null&&InfoList.size()>0){
                        //新增的时候存在相通标准的编码
                        json.put("promptkey","ishave");
                        json.put("promptValue","该动态测量项编码已存在!");
                    }else{
                        item.setId(Integer.parseInt(id));
                        if(item_max_value!=null&&!item_max_value.equals(""))
                            item.setItem_max_value(Float.valueOf(item_max_value));
                        if(item_min_value!=null&&!item_min_value.equals(""))
                            item.setItem_min_value(Float.valueOf(item_min_value));
                        if(item_frequency!=null&&!item_frequency.equals(""))
                            item.setItem_frequency(Float.valueOf(item_frequency));
                        if(item_std_value!=null&&!item_std_value.equals(""))
                            item.setItem_std_value(Float.valueOf(item_std_value));
                        if(item_pos_deviation_value!=null&&!item_pos_deviation_value.equals(""))
                            item.setItem_pos_deviation_value(Float.valueOf(item_pos_deviation_value));
                        if(item_neg_deviation_value!=null&&!item_neg_deviation_value.equals(""))
                            item.setItem_neg_deviation_value(Float.valueOf(item_neg_deviation_value));
                        if(ovality_max!=null&&!ovality_max.equals(""))
                            item.setOvality_max(Float.valueOf(ovality_max));
                        item.setMeasure_item_code(measure_item_code);
                        item.setThread_acceptance_criteria_no(thread_acceptance_criteria_no);
                        if(both_ends!=null&&!both_ends.equals(""))
                          item.setBoth_ends(both_ends);
                        if(reading_types!=null&&!reading_types.equals(""))
                          item.setReading_types(reading_types);
                        resTotal=dynamicMeasurementItemDao.updateDynamicMeasurementItem(item);
                        if(resTotal>0){
                            json.put("promptkey","success");
                        }else{
                            json.put("promptkey","fail2");
                            json.put("promptValue","保存失败");
                        }
                    }
                }else{
                    //新增
                    List<DynamicMeasurementItem>InfoList=dynamicMeasurementItemDao.getDynamicMeasurementItemByItemCodeOfAdd(measure_item_code,thread_acceptance_criteria_no);
                    if (InfoList!=null&&InfoList.size()>0){
                         //新增的时候存在相通标准的编码
                        json.put("promptkey","ishave");
                        json.put("promptValue","该动态测量项编码已存在!");
                    }else{
                        if(item_max_value!=null&&!item_max_value.equals(""))
                            item.setItem_max_value(Float.valueOf(item_max_value));
                        if(item_min_value!=null&&!item_min_value.equals(""))
                            item.setItem_min_value(Float.valueOf(item_min_value));
                        if(item_frequency!=null&&!item_frequency.equals(""))
                            item.setItem_frequency(Float.valueOf(item_frequency));
                        if(item_std_value!=null&&!item_std_value.equals(""))
                            item.setItem_std_value(Float.valueOf(item_std_value));
                        if(item_pos_deviation_value!=null&&!item_pos_deviation_value.equals(""))
                            item.setItem_pos_deviation_value(Float.valueOf(item_pos_deviation_value));
                        if(item_neg_deviation_value!=null&&!item_neg_deviation_value.equals(""))
                            item.setItem_neg_deviation_value(Float.valueOf(item_neg_deviation_value));
                        if(ovality_max!=null&&!ovality_max.equals(""))
                            item.setOvality_max(Float.valueOf(ovality_max));
                        item.setMeasure_item_code(measure_item_code);
                        item.setThread_acceptance_criteria_no(thread_acceptance_criteria_no);
                        if(both_ends!=null&&!both_ends.equals(""))
                            item.setBoth_ends(both_ends);
                        if(reading_types!=null&&!reading_types.equals(""))
                            item.setReading_types(reading_types);
                        resTotal=dynamicMeasurementItemDao.addDynamicMeasurementItem(item);
                        //接收id的值
                        if(resTotal>0){
                            json.put("promptkey","success");
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
    //删除Function信息
    @RequestMapping("/delDynamicMeasureItem")
    @ResponseBody
    public String delDynamicMeasureItem(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JSONObject json=new JSONObject();
        try{
            String hlparam=request.getParameter("hlparam");
            StringBuilder sbmessage = new StringBuilder();
            if(hlparam!=null){
                String[]idArr=hlparam.split(",");
                int resTotal=0;
                resTotal=dynamicMeasurementItemDao.delDynamicMeasurementItem(idArr);
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
    //获取静态测量项的下拉框信息
    //获取所有下拉接收标准
    @RequestMapping("/getAllDropDownAcceptanceCriteria")
    @ResponseBody
    public String getAllDropDownAcceptanceCriteria(@RequestParam(value = "thread_acceptance_criteria_no",required = false)String thread_acceptance_criteria_no,HttpServletRequest request){
        List<ComboxItem> colist=new ArrayList<>();
        if(thread_acceptance_criteria_no!=null&&!thread_acceptance_criteria_no.equals("")){
            List<HashMap<String,Object>>list=dynamicMeasurementItemDao.getAllDropDownStaticMeasureItem(thread_acceptance_criteria_no);
            for(int i=0;i<list.size();i++){
                ComboxItem citem= new ComboxItem();
                HashMap<String,Object> hs=list.get(i);
                citem.id=String.valueOf(hs.get("measure_item_code"));
                citem.text=String.valueOf(hs.get("measure_item_name"));
                colist.add(citem);
            }
        }
        String map= JSONObject.toJSONString(colist);
        return map;
    }
    //获取所有的静态测量项的编码和名字
    @RequestMapping(value = "/getAllDropdownMeasureItemByInspectionNo",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getAllDropdownMeasureItemByInspectionNo(HttpServletRequest request,HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            String thread_inspection_record_code=request.getParameter("thread_inspection_record_code");
            List<HashMap<String,Object>>hsList=dynamicMeasurementItemDao.getAllDropdownItemRecordByInspectionNo(thread_inspection_record_code);
            String mmp= JSONArray.toJSONString(hsList);
            System.out.println(mmp+"-----------");
            json.put("promptkey","success");
            json.put("promptValue",mmp);
        }catch (Exception e){
            json.put("promptkey","error");
            json.put("promptValue",e.getMessage());
        }finally {
            try{
                ResponseUtil.write(response,json);
            }catch (Exception e){

            }
        }
        return null;
    }
}
