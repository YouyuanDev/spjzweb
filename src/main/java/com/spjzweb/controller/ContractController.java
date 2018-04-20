package com.spjzweb.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spjzweb.dao.ContractInfoDao;
import com.spjzweb.entity.ContractInfo;
import com.spjzweb.util.ExcelUtil;
import com.spjzweb.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping("/Contract")
public class ContractController {
    @Autowired
    private ContractInfoDao contractInfoDao;
    //搜索
    @RequestMapping(value = "getContractAllByLike",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getContractAllByLike(@RequestParam(value = "contract_no",required = false)String contract_no,HttpServletRequest request){
        String page= request.getParameter("page");
        String rows= request.getParameter("rows");
        if(page==null){
            page="1";
        }
        if(rows==null){
            rows="20";
        }
        int start=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
        List<HashMap<String,Object>> list=contractInfoDao.getAllByLike(contract_no,start,Integer.parseInt(rows));
        int count=contractInfoDao.getCountAllByLike(contract_no);
        Map<String,Object> maps=new HashMap<String,Object>();
        maps.put("total",count);
        maps.put("rows",list);
        String mmp= JSONArray.toJSONString(list);
        //System.out.print("mmp:"+mmp);
        return mmp;

    }
    //保存function
    @RequestMapping(value = "/saveContract")
    @ResponseBody
    public String saveContract(ContractInfo contractInfo, HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            int resTotal=0;
            //先判断是否存在相通的合同编号
            List<ContractInfo>contractInfoList=null;
            if(contractInfo.getId()==0){
                contractInfoDao.getContractInfoByContractNoOfAdd(contractInfo.getContract_no());
            }else{
                contractInfoDao.getContractInfoByContractNoOfEdit(contractInfo.getContract_no());
            }
            if(contractInfoList!=null&&contractInfoList.size()>0){
                json.put("promptkey","fail1");
                json.put("promptValue","该合同编号已存在!");
            }else{
                if(contractInfo.getId()==0){
                    //添加
                    resTotal=contractInfoDao.addContractInfo(contractInfo);
                }else{
                    //修改！
                    resTotal=contractInfoDao.updateContractInfo(contractInfo);
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
    @RequestMapping("/delContract")
    public String delContract(@RequestParam(value = "hlparam")String hlparam,HttpServletResponse response)throws Exception{
        String[]idArr=hlparam.split(",");
        int resTotal=0;
        resTotal=contractInfoDao.delContractInfo(idArr);
        JSONObject json=new JSONObject();
        StringBuilder sbmessage = new StringBuilder();
        sbmessage.append("总共");
        sbmessage.append(Integer.toString(resTotal));
        sbmessage.append("项合同信息删除成功\n");
        if(resTotal>0){
            json.put("success",true);
        }else{
            json.put("success",false);
        }
        json.put("message",sbmessage.toString());
        ResponseUtil.write(response,json);
        return null;
    }

    //合同录入
    @RequestMapping(value = "/uploadContractList",method = RequestMethod.POST)
    public String uploadContractList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            String saveDirectory = request.getSession().getServletContext().getRealPath("/upload/pipes");
            File uploadPath = new File(saveDirectory);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }
            //FileRenameUtil util = new FileRenameUtil();
            MultipartRequest multi = new MultipartRequest(request, saveDirectory, 100* 1024 * 1024, "UTF-8");
            Enumeration files = multi.getFileNames();
            String newName = "";
            File file=null;
            int TotalUploadedPipes=0;
            int TotalSkippedPipes=0;
            if (files.hasMoreElements()) {
                String name = (String) files.nextElement();
                file = multi.getFile(name);
                if (file != null) {
                    newName = file.getName();
                    //处理excel文件
//                    HashMap retMap =importExcelInfo(saveDirectory+"/"+newName,overwrite,inODBareStorage);
//                    TotalUploadedPipes=Integer.parseInt(retMap.get("uploaded").toString());
//                    TotalSkippedPipes=Integer.parseInt(retMap.get("skipped").toString());
                }
            }

            JSONObject json = new JSONObject();
            json.put("fileUrl", newName);
            json.put("totaluploaded",TotalUploadedPipes);
            json.put("totalskipped",TotalSkippedPipes);
            json.put("success",true);
            ResponseUtil.write(response, json);
            System.out.print("uploadPipeList成功");
            System.out.println("saveDirectory File="+saveDirectory+"/"+newName);
            System.out.println("file.length()="+file.length());
        } catch (Exception e) {
            System.err.println("Exception=" + e.getMessage().toString());
            e.printStackTrace();
            JSONObject json = new JSONObject();
            json.put("success",false);
            ResponseUtil.write(response, json);
        }
        return null;
    }
//    public HashMap importExcelInfo( String fullfilename,boolean overwrite, boolean inODBareStorage) throws Exception{
//
//        HashMap retMap = new HashMap();//返回值
//        int TotalUploaded=0;//成功插入数据库的钢管数量
//        int TotalSkipped=0; //无合同号存在跳过的钢管数量
//
//
//        List<List<Object>> listob = ExcelUtil.readFromFiletoList(fullfilename);
//        //遍历listob数据，把数据放到List中
//
//        for (int i = 0; i < listob.size(); i++) {
//            List<Object> ob = listob.get(i);
//            PipeBasicInfo pipe = new PipeBasicInfo();
//            //设置编号
//            System.out.println(String.valueOf(ob.get(ExcelUtil.PIPE_NO_INDEX)));
//            //通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
//            System.out.println("row:"+String.valueOf(i));
//            System.out.println("listob.size():"+String.valueOf(listob.size()));
//
//
////                if(!ExcelUtil.isNumeric00(String.valueOf(ob.get(ExcelUtil.PIPE_NO_INDEX)))){
////                    //若管号为空或不为数字，则跳过
////                    continue;
////                }
//            if(!ExcelUtil.isNumeric00(String.valueOf(ob.get(ExcelUtil.OD_INDEX)))){
//                //若od为空或不为数字，则跳过
//                continue;
//            }
//            if(!ExcelUtil.isNumeric00(String.valueOf(ob.get(ExcelUtil.WT_INDEX)))){
//                //若wt为空或不为数字，则跳过
//                continue;
//            }
//            if(!ExcelUtil.isNumeric00(String.valueOf(ob.get(ExcelUtil.WEIGHT_INDEX)))){
//                //若weight为空或不为数字，则跳过
//                continue;
//            }
//            if(!ExcelUtil.isNumeric00(String.valueOf(ob.get(ExcelUtil.P_LENGTH_INDEX)))){
//                //若length为空或不为数字，则跳过
//                continue;
//            }
//
//            pipe.setId(0);
//            pipe.setPipe_no(String.valueOf(ob.get(ExcelUtil.PIPE_NO_INDEX)));
//            pipe.setContract_no(String.valueOf(ob.get(ExcelUtil.CONTRACT_NO_INDEX)));
//            pipe.setOd(Float.parseFloat(ob.get(ExcelUtil.OD_INDEX).toString()));
//            pipe.setWt(Float.parseFloat(ob.get(ExcelUtil.WT_INDEX).toString()));
//            pipe.setHeat_no(String.valueOf(ob.get(ExcelUtil.HEAT_NO_INDEX)));
//            pipe.setPipe_making_lot_no(String.valueOf(ob.get(ExcelUtil.PIPE_MAKING_LOT_NO_INDEX)));
//            pipe.setWeight(Float.parseFloat(ob.get(ExcelUtil.WEIGHT_INDEX).toString()));
//            pipe.setP_length(Float.parseFloat(ob.get(ExcelUtil.P_LENGTH_INDEX).toString()));
//            pipe.setStatus("bare1");
//
//
//            //批量插入
//            int res=0;
//
//            //查找该contract是否存在
//            List<ContractInfo>conlist=contractInfoDao.getContractInfoByContractNo(pipe.getContract_no());
//            if(conlist.size()==0){
//                TotalSkipped=TotalSkipped+1;
//                continue;//不存在则此钢管不予以录入系统
//            }
//            //检查pipe的钢种信息是否为空,如果是从contract里得到钢种信息并赋值
//            if(pipe.getGrade()==null||pipe.getGrade().equals("")){
//                pipe.setGrade(((ContractInfo)conlist.get(0)).getGrade());
//            }
//
//            //查找该pipebasicinfo是否存在
//            List<PipeBasicInfo>pipelist=pipeBasicInfoDao.getPipeNumber(pipe.getPipe_no());
//            if(pipelist.size()==0){
//                //新钢管入库,如od库或id库
//                if(inODBareStorage) {
//                    pipe.setStatus("bare1");
//                }else{
//                    pipe.setStatus("bare2");
//                }
//
//                res = pipeBasicInfoDao.addPipeBasicInfo(pipe);
//                System.out.println("Insert res: " + res);
//            }else{
//
//                if(overwrite) {
//                    //更新数据库旧记录
//                    PipeBasicInfo oldpipeinfo = pipelist.get(0);
//                    pipe.setId(oldpipeinfo.getId());
//                    pipe.setStatus(oldpipeinfo.getStatus());
//                    res = pipeBasicInfoDao.updatePipeBasicInfo(pipe);
//                    System.out.println("Update res: " + res);
//                }
//
//            }
//
//
//            TotalUploaded=TotalUploaded+res;
//        }
//        System.out.println("Total pipes: "+TotalUploaded);
//        retMap.put("uploaded",TotalUploaded);
//        retMap.put("skipped",TotalSkipped);
//
//        return retMap;
//    }
}
