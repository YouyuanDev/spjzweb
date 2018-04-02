package com.spjzweb.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spjzweb.dao.ToolMeasuringRecordDao;
import com.spjzweb.entity.ToolMeasuringRecord;
import com.spjzweb.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ToolOperation")
public class ToolMeasuringRecordController {
   
    @Autowired
    private ToolMeasuringRecordDao toolMeasuringRecordDao;

    @RequestMapping("/saveToolMeasuringRecord")
    @ResponseBody
    public String saveToolMeasuringRecord(ToolMeasuringRecord toolMeasuringRecord, HttpServletRequest request, HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            int resTotal=0;
            if(toolMeasuringRecord.getId()==0){
                //添加
                resTotal=toolMeasuringRecordDao.addToolMeasuringRecord(toolMeasuringRecord);
            }else{
                //修改！
                resTotal=toolMeasuringRecordDao.updateToolMeasuringRecord(toolMeasuringRecord);
            }
            if(resTotal>0){
                json.put("success",true);
                json.put("message","保存成功");
            }else{
                json.put("success",false);
                json.put("message","保存失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            json.put("success",false);
            json.put("message",e.getMessage());

        }finally {
            try {
                ResponseUtil.write(response, json);
            }catch  (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @RequestMapping("/delToolMeasuringRecord")
    public String delToolMeasuringRecord(@RequestParam(value = "hlparam")String hlparam,HttpServletResponse response)throws Exception{
        String[]idArr=hlparam.split(",");
        int resTotal=0;
        resTotal=toolMeasuringRecordDao.delToolMeasuringRecord(idArr);
        JSONObject json=new JSONObject();
        StringBuilder sbmessage = new StringBuilder();
        sbmessage.append("总共");
        sbmessage.append(Integer.toString(resTotal));
        sbmessage.append("项工具记录信息删除成功\n");
        if(resTotal>0){
            //System.out.print("删除成功");
            json.put("success",true);
        }else{
            //System.out.print("删除失败");
            json.put("success",false);
        }
        json.put("message",sbmessage.toString());
        ResponseUtil.write(response,json);
        return null;
    }

    @RequestMapping(value = "/getAllByLike")
    @ResponseBody
    public String getAllByLike(@RequestParam(value = "tool_measuring_record_no",required = false)String tool_measuring_record_no,HttpServletRequest request){
        String page= request.getParameter("page");
        String rows= request.getParameter("rows");
        if(page==null){
            page="1";
        }
        if(rows==null){
            rows="20";
        }
        int start=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
        List<HashMap<String,Object>>list=toolMeasuringRecordDao.getAllByLike(tool_measuring_record_no,start,Integer.parseInt(rows));
        int count=toolMeasuringRecordDao.getCountAllByLike(tool_measuring_record_no);
        Map<String,Object> maps=new HashMap<String,Object>();
        maps.put("total",count);
        maps.put("rows",list);
        String mmp= JSONArray.toJSONString(maps);
        return mmp;
    }
    @RequestMapping(value = "/getAllToolRecord")
    @ResponseBody
    public String getAllToolRecord(@RequestParam(value = "tool_measuring_record_no",required = false)String tool_measuring_record_no,HttpServletRequest request){
        List<ToolMeasuringRecord>list=toolMeasuringRecordDao.getAllToolRecordByNo(tool_measuring_record_no);
        String mmp= JSONArray.toJSONString(list);
        System.out.println(mmp);
        return mmp;
    }
    @RequestMapping(value = "/getToolRecordByNo")
    @ResponseBody
    public String getToolRecordByNo(@RequestParam(value = "tool_measuring_record_no",required = false)String tool_measuring_record_no,HttpServletRequest request){
        ToolMeasuringRecord tool=toolMeasuringRecordDao.getToolRecordByNo(tool_measuring_record_no);
        String mmp= JSONArray.toJSONString(tool);
        System.out.println("获取单个记录"+mmp);
        return mmp;
    }
}
