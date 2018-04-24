package com.spjzweb.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spjzweb.dao.ThreadInspectionRecordDao;
import com.spjzweb.entity.ThreadInspectionRecord;
import com.spjzweb.util.PropertyReader;
import com.spjzweb.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ThreadingOperation")
public class ThreadInspectionRecordController {
   
    @Autowired
    private ThreadInspectionRecordDao threadInspectionRecordDao;
    @RequestMapping(value = "/getThreadInspectionAllByLike")
    @ResponseBody
    public String getThreadInspectionAllByLike(@RequestParam(value = "contract_no",required = false)String contract_no,@RequestParam(value = "couping_no",required = false)String couping_no, @RequestParam(value = "operator_no",required = false)String operator_no, @RequestParam(value = "begin_time",required = false)String begin_time, @RequestParam(value = "end_time",required = false)String end_time, HttpServletRequest request){
        String page= request.getParameter("page");
        String rows= request.getParameter("rows");
        System.out.println("contract_no="+contract_no);
        System.out.println("couping_no="+couping_no);
        System.out.println("operator_no="+operator_no);
        System.out.println("beginTime="+begin_time);
        System.out.println("endTime="+end_time);
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
        List<HashMap<String,Object>>list=threadInspectionRecordDao.getAllByLike(contract_no,couping_no,operator_no,beginTime,endTime,start,Integer.parseInt(rows));
        int count=threadInspectionRecordDao.getCountAllByLike(contract_no,couping_no,operator_no,beginTime,endTime);
        Map<String,Object> maps=new HashMap<String,Object>();
        maps.put("total",count);
        maps.put("rows",list);
        String mmp= JSONArray.toJSONString(maps);
        return mmp;
    }
    @RequestMapping("/saveThreadingProcess")
    @ResponseBody
    public String saveThreadingProcess(ThreadInspectionRecord threadingProcess, HttpServletRequest request, HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            String odbptime= request.getParameter("instime");
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
                threadingProcess.setThread_inspection_record_code(String.valueOf(System.currentTimeMillis()));
                resTotal=threadInspectionRecordDao.addThreadInspectionRecord(threadingProcess);
            }else{
                //修改！
                if(threadingProcess.getThread_inspection_record_code()!=null&&!threadingProcess.getThread_inspection_record_code().equals("")){
                    resTotal=threadInspectionRecordDao.updateThreadInspectionRecord(threadingProcess);
                }
            }
            System.out.println("-----"+resTotal);
            if(resTotal>0){
                json.put("promptkey","success");
                json.put("promptValue","保存成功");
            }else{
                json.put("promptkey","fail");
                json.put("promptValue","保存失败");
            }
        }catch (Exception e){
            json.put("promptkey","fail");
            json.put("promptValue",e.getMessage());
            e.printStackTrace();

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
        resTotal=threadInspectionRecordDao.delThreadInspectionRecord(idArr);
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
    @RequestMapping("/getVideoAddress")
    public String getVideoAddress(HttpServletResponse response)throws Exception{
        JSONObject json=new JSONObject();
        String videoAddress= PropertyReader.getVideoAddress();
        json.put("message",videoAddress);
        ResponseUtil.write(response,json);
        return null;
    }
    @RequestMapping("/uploadVideoFile")
    @ResponseBody
    public String uploadVideoFile(HttpServletRequest request,HttpServletResponse response){
        String flag="success";
        try{
            response.setContentType("text/html;charset=UTF-8");
            // 读取请求Body
            byte[] body = readBody(request);
            // 取得所有Body内容的字符串表示
            String textBody = new String(body, "ISO-8859-1");
            // 取得上传的文件名称
            String fileName = getFileName(textBody);
            // 取得文件开始与结束位置
            String contentType = request.getContentType();
            String boundaryText = contentType.substring(contentType.lastIndexOf("=") + 1, contentType.length());
            // 取得实际上传文件的气势与结束位置
            int pos = textBody.indexOf("filename=\"");
            pos = textBody.indexOf("\n", pos) + 1;
            pos = textBody.indexOf("\n", pos) + 1;
            pos = textBody.indexOf("\n", pos) + 1;
            int boundaryLoc = textBody.indexOf(boundaryText, pos) - 4;
            int begin = ((textBody.substring(0, pos)).getBytes("ISO-8859-1")).length;
            int end = ((textBody.substring(0, boundaryLoc)).getBytes("ISO-8859-1")).length;
            //保存到本地
            writeToDir(request,fileName,body,begin,end);
        }catch (Exception e){
            flag="error";
            e.printStackTrace();
        }
        try{
            ResponseUtil.write(response,flag);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private byte[] readBody(HttpServletRequest request) throws Exception {
        // 获取请求文本字节长度
        int formDataLength = request.getContentLength();
        // 取得ServletInputStream输入流对象
        DataInputStream dataStream = new DataInputStream(request.getInputStream());
        byte body[] = new byte[formDataLength];
        int totalBytes = 0;
        while (totalBytes < formDataLength) {
            int bytes = dataStream.read(body, totalBytes, formDataLength);
            totalBytes += bytes;
        }
        return body;
    }

    private String getFileName(String requestBody) {
        String fileName = requestBody.substring(requestBody.indexOf("filename=\"") + 10);
        fileName = fileName.substring(0, fileName.indexOf("\n"));
        fileName = fileName.substring(fileName.indexOf("\n") + 1, fileName.indexOf("\""));
        return fileName;
    }

    private void writeToDir(HttpServletRequest request,String fileName, byte[] body, int begin, int end) throws Exception {
        String saveDirectory = request.getSession().getServletContext().getRealPath("/upload/videos");
        File uploadPath = new File(saveDirectory);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        fileName=saveDirectory+"/"+fileName;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        fileOutputStream.write(body, begin, (end - begin));
        fileOutputStream.flush();
        fileOutputStream.close();
    }

//    @RequestMapping(value = "/saveThreadingProcessByWinform")
//    @ResponseBody
//    public String saveThreadingProcessByWinform(HttpServletRequest request,HttpServletResponse response){
//        StringBuilder sb=new StringBuilder();
//        JSONObject jsonReturn=new JSONObject();
//        try{
//            BufferedReader reader=request.getReader();
//            String input=null;
//            while ((input=reader.readLine())!=null){
//                sb.append(input);
//            }
//
//            JSONObject json=JSONObject.parseObject(sb.toString());
//            String arg1=json.getString("arg1");
//            String arg2=json.getString("arg2");
//            String arg3=json.getString("arg3");
//            String arg4=json.getString("arg4");
//            String arg5=json.getString("arg5");
//            String arg6=json.getString("arg6");
//            String arg7=json.getString("arg7");
//            String arg8=json.getString("arg8");
//            String arg9=json.getString("arg9");
//            String arg10=json.getString("arg10");
//            String arg11=json.getString("arg11");
//            String arg12=json.getString("arg12");
//            String arg13=json.getString("arg13");
//            String arg14=json.getString("arg14");
//            String arg15=json.getString("arg15");
//            String arg16=json.getString("arg16");
//            String arg17=json.getString("arg17");
//            String arg44=json.getString("arg43");
//            String arg45=json.getString("arg44");
//            String arg46=json.getString("arg45");
//            String arg47=json.getString("arg46");
//            String arg48=json.getString("arg47");
//            String arg49=json.getString("arg48");
//            String arg50=json.getString("arg49");
//            ToolMeasuringRecord record=new ToolMeasuringRecord();
//            record.setTool_measuring_record_no(arg1);
//            record.setThread_pitch_gauge_no(arg2);
//            record.setThread_pitch_calibration_framwork(arg3);
//            record.setSealing_surface_gauge_no(arg4);
//            record.setSealing_surface_calibration_ring_no(arg5);
//            record.setDepth_caliper_no(arg6);
//            record.setThreading_distance_gauge_no(arg7);
//            record.setThread_distance_calibration_sample_no(arg8);
//            record.setTaper_gauge_no(arg9);
//            record.setTooth_height_gauge_no(arg10);
//            record.setTooth_height_calibration_sample_no(arg11);
//            record.setTooth_width_stop_gauge_no(arg12);
//            record.setThread_min_length_sample_no(arg13);
//            record.setCoupling_length_sample_no(arg14);
//            record.setCaliper_no(arg15);
//            record.setCaliper_tolerance(arg16);
//            record.setCollar_gauge_no(arg17);
//            record.setContract_no(arg44);
//            record.setHeat_no(arg45);
//            record.setTest_batch_no(arg46);
//            record.setSteel_grade(arg47);
//            record.setTexture(arg48);
//            record.setProduction_area(arg49);
//            record.setMachine_no(arg50);
//
//
//            int resTotal=toolMeasuringRecordDao.addToolMeasuringRecord(record);
//            if(resTotal>0){
//                String arg18=json.getString("arg18");
//                String arg19=json.getString("arg19");
//                String arg20=json.getString("arg20");
//                String arg21=json.getString("arg21");
//
//                String arg22=json.getString("arg22");
//                String arg23=json.getString("arg23");
//                String arg24=json.getString("arg24");
//                String arg25=json.getString("arg25");
//                String arg26=json.getString("arg26");
//                String arg27=json.getString("arg27");
//                String arg28=json.getString("arg28");
//                String arg29=json.getString("arg29");
//                String arg30=json.getString("arg30");
//                String arg31=json.getString("arg31");
//                String arg32=json.getString("arg32");
//                String arg33=json.getString("arg33");
//                String arg34=json.getString("arg34");
//                String arg35=json.getString("arg35");
//                String arg36=json.getString("arg36");
//                String arg37=json.getString("arg37");
//                String arg38=json.getString("arg38");
//                String arg39=json.getString("arg39");
//                String arg40=json.getString("arg40");
//                String arg51=json.getString("arg50");
//                float arg22_1=0,arg23_1=0,arg24_1=0,arg25_1=0,arg26_1=0,arg27_1=0,
//                        arg28_1=0,arg29_1=0,arg30_1=0,arg31_1=0,arg32_1=0,arg33_1=0,
//                        arg34_1=0,arg35_1=0,arg36_1=0,arg37_1=0,arg38_1=0,arg39_1=0,arg40_1=0;
//                if(arg22!=null&&arg22!="")
//                    arg22_1=Float.valueOf(arg22);
//                if(arg23!=null&&arg23!="")
//                    arg23_1=Float.valueOf(arg23);
//                if(arg24!=null&&arg24!="")
//                    arg24_1=Float.valueOf(arg24);
//                if(arg25!=null&&arg25!="")
//                    arg25_1=Float.valueOf(arg25);
//                if(arg26!=null&&arg26!="")
//                    arg26_1=Float.valueOf(arg26);
//                if(arg27!=null&&arg27!="")
//                    arg27_1=Float.valueOf(arg27);
//                if(arg28!=null&&arg28!="")
//                    arg28_1=Float.valueOf(arg28);
//                if(arg29!=null&&arg29!="")
//                    arg29_1=Float.valueOf(arg29);
//                if(arg30!=null&&arg30!="")
//                    arg30_1=Float.valueOf(arg30);
//                if(arg31!=null&&arg31!="")
//                    arg31_1=Float.valueOf(arg31);
//                if(arg32!=null&&arg32!="")
//                    arg32_1=Float.valueOf(arg32);
//                if(arg33!=null&&arg33!="")
//                    arg33_1=Float.valueOf(arg33);
//                if(arg34!=null&&arg34!="")
//                    arg34_1=Float.valueOf(arg34);
//                if(arg35!=null&&arg35!="")
//                    arg35_1=Float.valueOf(arg35);
//                if(arg36!=null&&arg36!="")
//                    arg36_1=Float.valueOf(arg36);
//                if(arg37!=null&&arg37!="")
//                    arg37_1=Float.valueOf(arg37);
//                if(arg38!=null&&arg38!="")
//                    arg38_1=Float.valueOf(arg38);
//                if(arg39!=null&&arg39!="")
//                    arg39_1=Float.valueOf(arg39);
//                if(arg40!=null&&arg40!="")
//                    arg40_1=Float.valueOf(arg40);
//                String arg41=json.getString("arg41");
//                String arg42=json.getString("arg42");
//                String arg43=json.getString("arg43");
//                ThreadingProcess process=new ThreadingProcess();
//                process.setCouping_no(arg18);
//                process.setProcess_no(arg19);
//                process.setOperator_no(arg20);
//                process.setVisual_inspection(arg21);
//                process.setThread_tooth_pitch_diameter_max(arg22_1);
//                process.setThread_tooth_pitch_diameter_avg(arg23_1);
//                process.setThread_tooth_pitch_diameter_min(arg24_1);
//                process.setThread_sealing_surface_diameter_max(arg25_1);
//                process.setThread_sealing_surface_diameter_avg(arg26_1);
//                process.setThread_sealing_surface_diameter_min(arg27_1);
//                process.setThread_sealing_surface_ovality(arg28_1);
//                process.setThread_width(arg29_1);
//                process.setThread_pitch(arg30_1);
//                process.setThread_taper(arg31_1);
//                process.setThread_height(arg32_1);
//                process.setThread_length_min(arg33_1);
//                process.setThread_bearing_surface_width(arg34_1);
//                process.setCouping_inner_end_depth(arg35_1);
//                process.setThread_hole_inner_diameter(arg36_1);
//                process.setCouping_od(arg37_1);
//                process.setCouping_length(arg38_1);
//                process.setThread_tooth_angle(arg39_1);
//                process.setThread_throug_hole_size(arg40_1);
//                process.setVideo_no(arg41);
//                process.setTool_measuring_record_no(arg1);
//                process.setInspection_result(arg42);
//                process.setThread_acceptance_criteria_no(arg43);
//                process.setInspection_time(new Date());
//                process.setThread_acceptance_criteria_no(arg51);
//                int result=threadingProcessDao.addThreadingProcess(process);
//                if(result>0){
//                    jsonReturn.put("resultMsg",true);
//                }else {
//                    jsonReturn.put("resultMsg",false);
//                }
//            }else{
//                jsonReturn.put("resultMsg",false);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            jsonReturn.put("resultMsg",false);
//        }
//        try{
//            ResponseUtil.write(response,jsonReturn);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//    @RequestMapping(value = "/getThreadingProcessByLike")
//    @ResponseBody
//    public String getThreadingProcessByLike(HttpServletRequest request,HttpServletResponse response){
//        String mmp=null;
//        JSONObject jsonReturn=new JSONObject();
//        try{
//            StringBuilder sb=new StringBuilder();
//            BufferedReader reader=request.getReader();
//            String input=null;
//            while ((input=reader.readLine())!=null){
//                    sb.append(input);
//            }
//            JSONObject json=JSONObject.parseObject(sb.toString());
//            String couping_no=null;
//            String operator_no=null;
//            String begin_time=null;
//            String end_time=null;
//            String page= null;
//            String rows= null;
//            if(json!=null){
//                couping_no=json.getString("couping_no");
//                operator_no=json.getString("operator_no");
//                begin_time=json.getString("begin_time");
//                end_time=json.getString("end_time");
//                page= json.getString("pageCurrent");
//                rows= json.getString("pageSize");
//            }
//            if(page==null||page.length()<=0){
//                page="1";
//            }
//            if(rows==null||rows.length()<=0){
//                rows="40";
//            }
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date beginTime=null;
//            Date endTime=null;
//            if(begin_time!=null&&begin_time!=""){
//                beginTime=sdf.parse(begin_time);
//            }
//            if(end_time!=null&&end_time!=""){
//                endTime=sdf.parse(end_time);
//            }
//            int start=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
//            List<HashMap<String,Object>>list=threadingProcessDao.getAllByLike(couping_no,operator_no,beginTime,endTime,start,Integer.parseInt(rows));
//            int count=threadingProcessDao.getCountAllByLike(couping_no,operator_no,beginTime,endTime);
//            Map<String,Object> maps=new HashMap<String,Object>();
//            jsonReturn.put("total",count);
//            jsonReturn.put("rowsData",list);
//            //String map= JSONArray.toJSONString(maps);
////            int start=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
////            List<ThreadingProcess>list=threadingProcessDao.getThreadingProcess(couping_no,operator_no,beginTime,endTime,start,Integer.parseInt(rows));
////            int count=threadingProcessDao.getCount(couping_no,operator_no,beginTime,endTime);
////            jsonReturn.put("total",count);
////            jsonReturn.put("rowsData",list);
//            ResponseUtil.write(response,jsonReturn);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        try {
//            ResponseUtil.write(response,mmp);
//        }catch (Exception e){
//
//        }
//        return null;
//    }

}
