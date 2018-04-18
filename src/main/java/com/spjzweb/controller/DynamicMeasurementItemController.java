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
    @RequestMapping(value = "getDynamicMeasureItemByLike",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getDynamicMeasureItemByLike(@RequestParam(value = "measure_item_code",required = false)String measure_item_code,@RequestParam(value = "thread_acceptance_criteria_no",required = false)String thread_acceptance_criteria_no,HttpServletRequest request){
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
    //保存function
    @RequestMapping(value = "/saveDynamicMeasureItem")
    @ResponseBody
    public String saveDynamicMeasureItem(DynamicMeasurementItem dynamicMeasurementItem, HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            int resTotal=0;
            //先判断是否存在相通的合同编号
            List<DynamicMeasurementItem>InfoList=dynamicMeasurementItemDao.getDataByItemCodeAndAcceptanceCriteriaNo(dynamicMeasurementItem.getMeasure_item_code(),dynamicMeasurementItem.getThread_acceptance_criteria_no());
            if(InfoList!=null&&InfoList.size()>0){
                json.put("promptkey","fail1");
                json.put("promptValue","该动态测量项编码已存在!");
            }else{
                if(dynamicMeasurementItem.getId()==0){
                    //添加
                    resTotal=dynamicMeasurementItemDao.addDynamicMeasurementItem(dynamicMeasurementItem);
                }else{
                    //修改！
                    resTotal=dynamicMeasurementItemDao.updateDynamicMeasurementItem(dynamicMeasurementItem);
                }
                if(resTotal>0){
                    json.put("promptkey","success");
                    json.put("promptValue","保存成功");
                }else{
                    json.put("promptkey","fail2");
                    json.put("promptValue","保存失败");
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
    public String delDynamicMeasureItem(@RequestParam(value = "hlparam")String hlparam,HttpServletResponse response)throws Exception{
        String[]idArr=hlparam.split(",");
        int resTotal=0;
        resTotal=dynamicMeasurementItemDao.delDynamicMeasurementItem(idArr);
        JSONObject json=new JSONObject();
        StringBuilder sbmessage = new StringBuilder();
        sbmessage.append("总共");
        sbmessage.append(Integer.toString(resTotal));
        sbmessage.append("项动态测量项信息删除成功\n");
        if(resTotal>0){
            json.put("success",true);
        }else{
            json.put("success",false);
        }
        json.put("message",sbmessage.toString());
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
}
