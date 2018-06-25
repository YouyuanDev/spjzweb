package com.spjzweb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spjzweb.dao.ItemRecordDao;
import com.spjzweb.dao.ThreadInspectionRecordDao;
import com.spjzweb.entity.ItemRecord;
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
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/ThreadingOperation")
public class ThreadInspectionRecordController {
   
    @Autowired
    private ThreadInspectionRecordDao threadInspectionRecordDao;
    @Autowired
    private ItemRecordDao itemRecordDao;
    @RequestMapping(value = "/getThreadInspectionAllByLike")
    @ResponseBody
    public String getThreadInspectionAllByLike(@RequestParam(value = "contract_no",required = false)String contract_no,@RequestParam(value = "coupling_no",required = false)String coupling_no, @RequestParam(value = "operator_no",required = false)String operator_no, @RequestParam(value = "begin_time",required = false)String begin_time, @RequestParam(value = "end_time",required = false)String end_time, HttpServletRequest request){
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
        List<HashMap<String,Object>>list=threadInspectionRecordDao.getAllByLike(contract_no,coupling_no,operator_no,beginTime,endTime,start,Integer.parseInt(rows));
        int count=threadInspectionRecordDao.getCountAllByLike(contract_no,coupling_no,operator_no,beginTime,endTime);
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
            //System.out.println("-----"+resTotal);
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
        //String saveDirectory = request.getSession().getServletContext().getRealPath("/upload/videos");

        String saveDirectory = request.getSession().getServletContext().getRealPath("/");
        saveDirectory=saveDirectory.substring(0,saveDirectory.lastIndexOf('/'));
        System.out.println("saveDirector1="+saveDirectory);
        if(ClientAppUpdateController.isServerTomcat){
            saveDirectory=saveDirectory.substring(0,saveDirectory.lastIndexOf('/'));
        }
        saveDirectory=saveDirectory+"/upload/videos";

        File uploadPath = new File(saveDirectory);
        if (!uploadPath.exists()){
            uploadPath.mkdirs();
        }
        fileName=saveDirectory+"/"+fileName;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        fileOutputStream.write(body, begin, (end - begin));
        fileOutputStream.flush();
        fileOutputStream.close();
    }
    @RequestMapping(value = "/saveThreadInspectionRecordOfWinform")
    @ResponseBody
    public String saveThreadInspectionRecordOfWinform(HttpServletRequest request,HttpServletResponse response){
        StringBuilder sb=new StringBuilder();
        JSONObject jsonReturn=new JSONObject();
        try{

            BufferedReader reader=request.getReader();
            String input=null;
            while ((input=reader.readLine())!=null){
                sb.append(input);
            }
            String requestData= URLDecoder.decode(sb.toString(),"UTF-8");
            System.out.println(requestData);
            JSONObject json=JSONObject.parseObject(requestData);
            //判断是修改还是添加
            String isAdd=json.getString("isAdd");
            String thread_inspection_record_code="";
            String coupling_no=json.getString("coupling_no");
            String contract_no=json.getString("contract_no");
            String production_line=json.getString("production_line");
            String machine_no=json.getString("machine_no");
            String operator_no=json.getString("operator_no");
            String production_crew=json.getString("production_crew");
            String production_shift=json.getString("production_shift");
            String video_no=json.getString("video_no");
            String inspection_result=json.getString("inspection_result");
            String inspection_time=json.getString("inspection_time");
            String coupling_heat_no=json.getString("coupling_heat_no");
            String coupling_lot_no=json.getString("coupling_lot_no");
            String item_record=json.getString("item_record");//json数组
            Date inspectionTime=new Date();
            String measure_code="";
            if(isAdd.contains("edit")){
                thread_inspection_record_code=json.getString("thread_inspection_record_code");
            }else{
                thread_inspection_record_code=String.valueOf(System.currentTimeMillis());
            }
            ArrayList<ItemRecord>itemRecordList=new ArrayList<>();
            if(item_record!=null){
                JSONArray itemRecordArr=JSONArray.parseArray(item_record);
                for(int i=0;i<itemRecordArr.size();i++){
                    JSONObject jsonRecord= itemRecordArr.getJSONObject(i);
                    ItemRecord itemRecord=new ItemRecord();
                    itemRecord.setThread_inspection_record_code(thread_inspection_record_code);
                    itemRecord.setItemcode(jsonRecord.getString("itemcode"));
                    itemRecord.setItemvalue(jsonRecord.getString("itemvalue"));
                    itemRecord.setReading_max(jsonRecord.getString("reading_max"));
                    itemRecord.setReading_min(jsonRecord.getString("reading_min"));
                    itemRecord.setReading_avg(jsonRecord.getString("reading_avg"));
                    itemRecord.setReading_ovality(jsonRecord.getString("reading_ovality"));
                    itemRecord.setToolcode1(jsonRecord.getString("toolcode1"));
                    itemRecord.setToolcode2(jsonRecord.getString("toolcode2"));
                    itemRecord.setMeasure_sample1(jsonRecord.getString("measure_sample1"));
                    itemRecord.setMeasure_sample2(jsonRecord.getString("measure_sample2"));
                    itemRecordList.add(itemRecord);
                }
            }
             if(inspection_time!=null&&inspection_time!=""){
                SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                inspectionTime = simFormat.parse(inspection_time);
            }

            if(isAdd.contains("edit")){
                ThreadInspectionRecord entity=threadInspectionRecordDao.getThreadInspectionRecordByNo(thread_inspection_record_code);
                if(entity!=null){
                    entity.setCoupling_no(coupling_no);
                    entity.setContract_no(contract_no);
                    entity.setProduction_line(production_line);
                    entity.setMachine_no(machine_no);
                    entity.setOperator_no(operator_no);
                    entity.setProduction_crew(production_crew);
                    entity.setProduction_shift(production_shift);
                    entity.setVideo_no(video_no);
                    entity.setInspection_result(inspection_result);
                    entity.setInspection_time(inspectionTime);
                    entity.setCoupling_heat_no(coupling_heat_no);
                    entity.setCoupling_lot_no(coupling_lot_no);
                }
                int result0=threadInspectionRecordDao.updateThreadInspectionRecordByCode(entity);
                if(result0>0){
                    int result1=0;
                    for (int i=0;i<itemRecordList.size();i++){
                        ItemRecord itemRecord=itemRecordList.get(i);
                        result1=itemRecordDao.updataItemRecordByCodeSingle(itemRecord);
                    }
                    if(result1>0){
                        jsonReturn.put("rowsData","success");
                    }else{
                        jsonReturn.put("rowsData","fail");
                    }
                }else{
                    jsonReturn.put("rowsData","fail");
                }
            }else{

                ThreadInspectionRecord threadInspectionRecord=new ThreadInspectionRecord();
                threadInspectionRecord.setThread_inspection_record_code(thread_inspection_record_code);
                threadInspectionRecord.setCoupling_no(coupling_no);
                threadInspectionRecord.setContract_no(contract_no);
                threadInspectionRecord.setProduction_line(production_line);
                threadInspectionRecord.setMachine_no(machine_no);
                threadInspectionRecord.setOperator_no(operator_no);
                threadInspectionRecord.setProduction_crew(production_crew);
                threadInspectionRecord.setProduction_shift(production_shift);
                threadInspectionRecord.setVideo_no(video_no);
                threadInspectionRecord.setInspection_result(inspection_result);
                threadInspectionRecord.setInspection_time(inspectionTime);
                threadInspectionRecord.setCoupling_heat_no(coupling_heat_no);
                threadInspectionRecord.setCoupling_lot_no(coupling_lot_no);
                int threadInspectionResult=threadInspectionRecordDao.addThreadInspectionRecord(threadInspectionRecord);
                if(threadInspectionResult>0){

                    int itemRecordResult=itemRecordDao.addItemRecordByWinform(itemRecordList);
                    if(itemRecordResult>0){
                        jsonReturn.put("rowsData","success");
                    }
                }else{
                    jsonReturn.put("rowsData","fail");
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            jsonReturn.put("rowsData","fail");
        }
        try{
            ResponseUtil.write(response,jsonReturn);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //获取检验记录数据(winform)
    @RequestMapping(value = "/getThreadInspectionRecordOfWinform")
    @ResponseBody
    public String getThreadInspectionRecordOfWinform(HttpServletRequest request,HttpServletResponse response){
        JSONObject jsonReturn=new JSONObject();
        try{
            StringBuilder sb=new StringBuilder();
            BufferedReader reader=request.getReader();
            String input=null;
            while ((input=reader.readLine())!=null){
                    sb.append(input);
            }
            JSONObject json=JSONObject.parseObject(sb.toString());
            String operator_no=null,production_crew=null,production_shift=null,contract_no=null,threading_type=null,od=null,wt=null,pipe_heat_no=null,pipe_lot_no=null,beginTimeStr=null,endTimeStr=null;
            Date beginTime=null;
            Date endTime=null;
           // float odFloat=0,wtFloat=0;
            if(json!=null){
                //工号、班次、班别、合同号、螺纹类型、外径、壁厚、炉号、批号、开始时间、结束时间
                operator_no=json.getString("operator_no").trim();
                production_crew=json.getString("production_crew").trim();
                production_shift=json.getString("production_shift").trim();
                contract_no=json.getString("contract_no").trim();
                threading_type=json.getString("threading_type").trim();
                od=json.getString("od").trim();
                wt=json.getString("wt").trim();
                pipe_heat_no=json.getString("pipe_heat_no").trim();
                pipe_lot_no=json.getString("pipe_lot_no").trim();
                beginTimeStr=json.getString("beginTime").trim();
                endTimeStr=json.getString("endTime").trim();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(beginTimeStr!=null&&beginTimeStr!=""){
                    beginTime=sdf.parse(beginTimeStr);
                }
                if(endTimeStr!=null&&endTimeStr!=""){
                    endTime=sdf.parse(endTimeStr);
                }
            }
            List<ThreadInspectionRecord>list=threadInspectionRecordDao.getThreadInspectionRecordOfWinform(operator_no,production_crew,production_shift,contract_no,threading_type,od,wt,pipe_heat_no,pipe_lot_no,beginTime,endTime);
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
    //删除检验记录数据(winform)
    @RequestMapping(value = "/delThreadInspectionRecordOfWinform")
    @ResponseBody
    public String delThreadInspectionRecordOfWinform(HttpServletRequest request,HttpServletResponse response){
        JSONObject jsonReturn=new JSONObject();
        try{
            StringBuilder sb=new StringBuilder();
            BufferedReader reader=request.getReader();
            String input=null;
            while ((input=reader.readLine())!=null){
                sb.append(input);
            }
            JSONObject json=JSONObject.parseObject(sb.toString());
            String thread_inspection_record_code=null;
            if(json!=null){
                thread_inspection_record_code=json.getString("thread_inspection_record_code").trim();
            }
            if(thread_inspection_record_code!=null){
                int result=threadInspectionRecordDao.delThreadInspectionRecordOfWinform(thread_inspection_record_code);
                if(result>0){
                    //删除测量项记录
                    int result1=itemRecordDao.delItemRecordByCodeSingle(thread_inspection_record_code);
                    if(result1>0)
                        jsonReturn.put("rowsData",true);
                    else
                        jsonReturn.put("rowsData",false);
                }else{
                    jsonReturn.put("rowsData",false);
                }
            }else{
                jsonReturn.put("rowsData",false);
            }
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
