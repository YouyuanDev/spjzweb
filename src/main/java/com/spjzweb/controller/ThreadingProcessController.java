package com.spjzweb.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spjzweb.dao.ThreadingProcessDao;
import com.spjzweb.dao.ToolMeasuringRecordDao;
import com.spjzweb.entity.ThreadingProcess;
import com.spjzweb.entity.ToolMeasuringRecord;
import com.spjzweb.util.ResponseUtil;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ThreadingOperation")
public class ThreadingProcessController {
   
    @Autowired
    private ThreadingProcessDao threadingProcessDao;
    @Autowired
    private ToolMeasuringRecordDao toolMeasuringRecordDao;
    @RequestMapping("/saveThreadingProcess")
    @ResponseBody
    public String saveThreadingProcess(ThreadingProcess threadingProcess, HttpServletRequest request, HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            String odbptime= request.getParameter("inspectiontime");
            int resTotal=0;
            if(odbptime!=null&&odbptime!=""){
                SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date new_odbptime = simFormat.parse(odbptime);
                threadingProcess.setInspection_time(new_odbptime);
            }else{
                threadingProcess.setInspection_time(new Date());
            }
            if(threadingProcess.getId()==0){
                //添加
                resTotal=threadingProcessDao.addThreadingProcess(threadingProcess);

            }else{
                //修改！
                resTotal=threadingProcessDao.updateThreadingProcess(threadingProcess);

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
    @RequestMapping("/delThreadingProcess")
    public String delThreadingProcess(@RequestParam(value = "hlparam")String hlparam,HttpServletResponse response)throws Exception{
        String[]idArr=hlparam.split(",");
        int resTotal=0;
        resTotal=threadingProcessDao.delThreadingProcess(idArr);
        JSONObject json=new JSONObject();
        StringBuilder sbmessage = new StringBuilder();
        sbmessage.append("总共");
        sbmessage.append(Integer.toString(resTotal));
        sbmessage.append("项螺纹检验信息删除成功\n");
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
    public String getAllByLike(@RequestParam(value = "couping_no",required = false)String couping_no, @RequestParam(value = "operator_no",required = false)String operator_no, @RequestParam(value = "begin_time",required = false)String begin_time, @RequestParam(value = "end_time",required = false)String end_time, @RequestParam(value = "mill_no",required = false)String mill_no, HttpServletRequest request){
        String page= request.getParameter("page");
        String rows= request.getParameter("rows");
        if(page==null){
            page="1";
        }
        if(rows==null){
            rows="20";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginTime=null;
        Date endTime=null;
        try{
            if(begin_time!=null&&begin_time!=""){
                beginTime=sdf.parse(begin_time);
                System.out.println(beginTime.toString());
            }
            if(end_time!=null&&end_time!=""){
                endTime=sdf.parse(end_time);
                System.out.println(endTime.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        int start=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
        List<HashMap<String,Object>>list=threadingProcessDao.getAllByLike(couping_no,operator_no,beginTime,endTime,start,Integer.parseInt(rows));
        int count=threadingProcessDao.getCountAllByLike(couping_no,operator_no,beginTime,endTime);
        Map<String,Object> maps=new HashMap<String,Object>();
        maps.put("total",count);
        maps.put("rows",list);
        String mmp= JSONArray.toJSONString(maps);
        return mmp;
    }
    @RequestMapping(value = "/saveThreadingProcessByWinform")
    @ResponseBody
    public String saveThreadingProcessByWinform(HttpServletRequest request,HttpServletResponse response){
        StringBuilder sb=new StringBuilder();
        JSONObject jsonReturn=new JSONObject();
        try{
            BufferedReader reader=request.getReader();
            String input=null;
            while ((input=reader.readLine())!=null){
                sb.append(input);
            }

            JSONObject json=JSONObject.parseObject(sb.toString());
            System.out.println("日志输出"+json.toString());
            String arg1=json.getString("arg1");
            String arg2=json.getString("arg2");
            String arg3=json.getString("arg3");
            String arg4=json.getString("arg4");
            String arg5=json.getString("arg5");
            String arg6=json.getString("arg6");
            String arg7=json.getString("arg7");
            String arg8=json.getString("arg8");
            String arg9=json.getString("arg9");
            String arg10=json.getString("arg10");
            String arg11=json.getString("arg11");
            String arg12=json.getString("arg12");
            String arg13=json.getString("arg13");
            String arg14=json.getString("arg14");
            String arg15=json.getString("arg15");
            String arg16=json.getString("arg16");
            String arg17=json.getString("arg17");
            ToolMeasuringRecord record=new ToolMeasuringRecord();
            record.setTool_measuring_record_no(arg1);
            record.setThread_pitch_gauge_no(arg2);
            record.setThread_pitch_calibration_framwork(arg3);
            record.setSealing_surface_gauge_no(arg4);
            record.setSealing_surface_calibration_ring_no(arg5);
            record.setDepth_caliper_no(arg6);
            record.setThreading_distance_gauge_no(arg7);
            record.setThread_distance_calibration_sample_no(arg8);
            record.setTaper_gauge_no(arg9);
            record.setTooth_height_gauge_no(arg10);
            record.setTooth_height_calibration_sample_no(arg11);
            record.setTooth_width_stop_gauge_no(arg12);
            record.setThread_min_length_sample_no(arg13);
            record.setCoupling_length_sample_no(arg14);
            record.setCaliper_no(arg15);
            record.setCaliper_tolerance(arg16);
            record.setCollar_gauge_no(arg17);
            int resTotal=toolMeasuringRecordDao.addToolMeasuringRecord(record);
            if(resTotal>0){
                String arg18=json.getString("arg18");
                String arg19=json.getString("arg19");
                String arg20=json.getString("arg20");
                String arg21=json.getString("arg21");

                String arg22=json.getString("arg22");
                String arg23=json.getString("arg23");
                String arg24=json.getString("arg24");
                String arg25=json.getString("arg25");
                String arg26=json.getString("arg26");
                String arg27=json.getString("arg27");
                String arg28=json.getString("arg28");
                String arg29=json.getString("arg29");
                String arg30=json.getString("arg30");
                String arg31=json.getString("arg31");
                String arg32=json.getString("arg32");
                String arg33=json.getString("arg33");
                String arg34=json.getString("arg34");
                String arg35=json.getString("arg35");
                String arg36=json.getString("arg36");
                String arg37=json.getString("arg37");
                String arg38=json.getString("arg38");
                String arg39=json.getString("arg39");
                String arg40=json.getString("arg40");
                float arg22_1=0,arg23_1=0,arg24_1=0,arg25_1=0,arg26_1=0,arg27_1=0,
                        arg28_1=0,arg29_1=0,arg30_1=0,arg31_1=0,arg32_1=0,arg33_1=0,
                        arg34_1=0,arg35_1=0,arg36_1=0,arg37_1=0,arg38_1=0,arg39_1=0,arg40_1=0;
                if(arg22!=null&&arg22!="")
                    arg22_1=Float.valueOf(arg22);
                if(arg23!=null&&arg23!="")
                    arg23_1=Float.valueOf(arg23);
                if(arg24!=null&&arg24!="")
                    arg24_1=Float.valueOf(arg24);
                if(arg25!=null&&arg25!="")
                    arg25_1=Float.valueOf(arg25);
                if(arg26!=null&&arg26!="")
                    arg26_1=Float.valueOf(arg26);
                if(arg27!=null&&arg27!="")
                    arg27_1=Float.valueOf(arg27);
                if(arg28!=null&&arg28!="")
                    arg28_1=Float.valueOf(arg28);
                if(arg29!=null&&arg29!="")
                    arg29_1=Float.valueOf(arg29);
                if(arg30!=null&&arg30!="")
                    arg30_1=Float.valueOf(arg30);
                if(arg31!=null&&arg31!="")
                    arg31_1=Float.valueOf(arg31);
                if(arg32!=null&&arg32!="")
                    arg32_1=Float.valueOf(arg32);
                if(arg33!=null&&arg33!="")
                    arg33_1=Float.valueOf(arg33);
                if(arg34!=null&&arg34!="")
                    arg34_1=Float.valueOf(arg34);
                if(arg35!=null&&arg35!="")
                    arg35_1=Float.valueOf(arg35);
                if(arg36!=null&&arg36!="")
                    arg36_1=Float.valueOf(arg36);
                if(arg37!=null&&arg37!="")
                    arg37_1=Float.valueOf(arg37);
                if(arg38!=null&&arg38!="")
                    arg38_1=Float.valueOf(arg38);
                if(arg39!=null&&arg39!="")
                    arg39_1=Float.valueOf(arg39);
                if(arg40!=null&&arg40!="")
                    arg40_1=Float.valueOf(arg40);
                String arg41=json.getString("arg41");
                String arg42=json.getString("arg42");
                ThreadingProcess process=new ThreadingProcess();
                process.setCouping_no(arg18);
                process.setProcess_no(arg19);
                process.setOperator_no(arg20);
                process.setVisual_inspection(arg21);
                process.setThread_tooth_pitch_diameter_max(arg22_1);
                process.setThread_tooth_pitch_diameter_avg(arg23_1);
                process.setThread_tooth_pitch_diameter_min(arg24_1);
                process.setThread_sealing_surface_diameter_max(arg25_1);
                process.setThread_sealing_surface_diameter_avg(arg26_1);
                process.setThread_sealing_surface_diameter_min(arg27_1);
                process.setThread_sealing_surface_ovality(arg28_1);
                process.setThread_width(arg29_1);
                process.setThread_pitch(arg30_1);
                process.setThread_taper(arg31_1);
                process.setThread_height(arg32_1);
                process.setThread_length_min(arg33_1);
                process.setThread_bearing_surface_width(arg34_1);
                process.setCouping_inner_end_depth(arg35_1);
                process.setThread_hole_inner_diameter(arg36_1);
                process.setCouping_od(arg37_1);
                process.setCouping_length(arg38_1);
                process.setThread_tooth_angle(arg39_1);
                process.setThread_throug_hole_size(arg40_1);
                process.setVideo_no(arg41);
                process.setTool_measuring_record_no(arg1);
                process.setInspection_result(arg42);
                process.setInspection_time(new Date());
                int result=threadingProcessDao.addThreadingProcess(process);
                if(result>0){
                    jsonReturn.put("resultMsg",true);
                }else {
                    jsonReturn.put("resultMsg",false);
                }
            }else{
                jsonReturn.put("resultMsg",false);
            }
        }catch (Exception e){
            e.printStackTrace();
            jsonReturn.put("resultMsg",false);
        }
        try{
            ResponseUtil.write(response,jsonReturn);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping(value = "/getThreadingProcessByLike")
    @ResponseBody
    public String getThreadingProcessByLike(HttpServletRequest request,HttpServletResponse response){
        String mmp=null;
        JSONObject jsonReturn=new JSONObject();
        try{
            StringBuilder sb=new StringBuilder();
            BufferedReader reader=request.getReader();
            String input=null;
            while ((input=reader.readLine())!=null){
                    sb.append(input);
            }
            JSONObject json=JSONObject.parseObject(sb.toString());
            String couping_no=null;
            String operator_no=null;
            String begin_time=null;
            String end_time=null;
            String page= null;
            String rows= null;
            if(json!=null){
                couping_no=json.getString("couping_no");
                operator_no=json.getString("operator_no");
                begin_time=json.getString("begin_time");
                end_time=json.getString("end_time");
                page= json.getString("pageCurrent");
                rows= json.getString("pageSize");
            }
            if(page==null||page.length()<=0){
                page="1";
            }
            if(rows==null||rows.length()<=0){
                rows="40";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date beginTime=null;
            Date endTime=null;
            if(begin_time!=null&&begin_time!=""){
                beginTime=sdf.parse(begin_time);
            }
            if(end_time!=null&&end_time!=""){
                endTime=sdf.parse(end_time);
            }
            int start=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
            List<HashMap<String,Object>>list=threadingProcessDao.getAllByLike(couping_no,operator_no,beginTime,endTime,start,Integer.parseInt(rows));
            int count=threadingProcessDao.getCountAllByLike(couping_no,operator_no,beginTime,endTime);
            Map<String,Object> maps=new HashMap<String,Object>();
            jsonReturn.put("total",count);
            jsonReturn.put("rowsData",list);
            //String map= JSONArray.toJSONString(maps);
//            int start=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
//            List<ThreadingProcess>list=threadingProcessDao.getThreadingProcess(couping_no,operator_no,beginTime,endTime,start,Integer.parseInt(rows));
//            int count=threadingProcessDao.getCount(couping_no,operator_no,beginTime,endTime);
//            jsonReturn.put("total",count);
//            jsonReturn.put("rowsData",list);
            ResponseUtil.write(response,jsonReturn);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            ResponseUtil.write(response,mmp);
        }catch (Exception e){

        }
        return null;
    }
    @RequestMapping(value = "/getThreadingVideo")
    @ResponseBody
    public String getThreadingVideo(HttpServletRequest request,HttpServletResponse response){
        //Filestr
        return null;
    }
}
