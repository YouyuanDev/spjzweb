package com.spjzweb.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spjzweb.dao.StaticMeasurementItemDao;
import com.spjzweb.entity.StaticMeasurementItem;
import com.spjzweb.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/StaticMeasure")
public class StaticMeasurementItemController {
    @Autowired
    private StaticMeasurementItemDao staticMeasurementItemDao;
    //搜索
    @RequestMapping(value = "getStaticMeasureItemByLike",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getStaticMeasureItemByLike(@RequestParam(value = "measure_item_code",required = false)String measure_item_code,@RequestParam(value = "measure_item_name",required = false)String measure_item_name, HttpServletRequest request){
        String page= request.getParameter("page");
        String rows= request.getParameter("rows");
        if(page==null){
            page="1";
        }
        if(rows==null){
            rows="20";
        }
        int start=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
        List<HashMap<String,Object>> list=staticMeasurementItemDao.getAllByLike(measure_item_code,measure_item_name,start,Integer.parseInt(rows));
        int count=staticMeasurementItemDao.getCountAllByLike(measure_item_code,measure_item_name);
        Map<String,Object> maps=new HashMap<String,Object>();
        maps.put("total",count);
        maps.put("rows",list);
        String mmp= JSONArray.toJSONString(list);
        //System.out.print("mmp:"+mmp);
        return mmp;

    }
    //保存function
    @RequestMapping(value = "/saveStaticMeasureItem")
    @ResponseBody
    public String saveStaticMeasureItem(StaticMeasurementItem staticMeasurementItem, HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            int resTotal=0;
            //先判断是否存在相通的合同编号
            List<StaticMeasurementItem>InfoList=null;
            if(staticMeasurementItem.getId()==0){
                InfoList=staticMeasurementItemDao.getStaticMeasurementItemByItemCodeOfAdd(staticMeasurementItem.getMeasure_item_code());
            }else{
                InfoList=staticMeasurementItemDao.getStaticMeasurementItemByItemCodeOfEdit(staticMeasurementItem.getMeasure_item_code());
            }

            if(InfoList!=null&&InfoList.size()>0){
                json.put("promptkey","fail1");
                json.put("promptValue","该静态测量项编码已存在!");
            }else{
                if(staticMeasurementItem.getId()==0){
                    //添加
                    System.out.println("添加－－－－－－－－－");
                    resTotal=staticMeasurementItemDao.addStaticMeasurementItem(staticMeasurementItem);
                }else{
                    System.out.println("修改－－－－－－－－－");
                    //修改！
                    resTotal=staticMeasurementItemDao.updateStaticMeasurementItem(staticMeasurementItem);
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
    @RequestMapping("/delStaticMeasureItem")
    public String delStaticMeasureItem(@RequestParam(value = "hlparam")String hlparam,HttpServletResponse response)throws Exception{
        String[]idArr=hlparam.split(",");
        int resTotal=0;
        resTotal=staticMeasurementItemDao.delStaticMeasurementItem(idArr);
        JSONObject json=new JSONObject();
        StringBuilder sbmessage = new StringBuilder();
        sbmessage.append("总共");
        sbmessage.append(Integer.toString(resTotal));
        sbmessage.append("项静态测量项信息删除成功\n");
        if(resTotal>0){
            json.put("success",true);
        }else{
            json.put("success",false);
        }
        json.put("message",sbmessage.toString());
        ResponseUtil.write(response,json);
        return null;
    }
}

