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
    public String getContractAllByLike(@RequestParam(value = "contract_no",required = false)String contract_no,@RequestParam(value = "customer_spec",required = false)String customer_spec,@RequestParam(value = "od",required = false)String od,@RequestParam(value = "wt",required = false)String wt,@RequestParam(value = "threading_type",required = false)String threading_type,HttpServletRequest request){
        String page= request.getParameter("page");
        String rows= request.getParameter("rows");
        if(page==null){
            page="1";
        }
        if(rows==null){
            rows="20";
        }
        int start=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
        List<HashMap<String,Object>> list=contractInfoDao.getAllByLike(contract_no,customer_spec,od,wt,threading_type,start,Integer.parseInt(rows));
        int count=contractInfoDao.getCountAllByLike(contract_no,customer_spec,od,wt,threading_type);
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
            String saveDirectory = request.getSession().getServletContext().getRealPath("/upload/contracts");
            File uploadPath = new File(saveDirectory);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }
            //FileRenameUtil util = new FileRenameUtil();
            System.out.println("saveDirectory="+saveDirectory);
            MultipartRequest multi = new MultipartRequest(request, saveDirectory, 100* 1024 * 1024, "UTF-8");
            System.out.println("11111111111111");
            Enumeration files = multi.getFileNames();

            System.out.println("2222222222");
            String newName = "";
            File file=null;
            HashMap retMap=null;
            if (files.hasMoreElements()) {
                String name = (String) files.nextElement();
                System.out.println("name="+name);
                file = multi.getFile(name);
                if (file != null) {
                    newName = file.getName();

                    //处理excel文件
                      retMap =importExcelInfo(saveDirectory+"/"+newName);
                }
            }

            JSONObject json = new JSONObject();
            json.put("fileUrl", newName);
            json.put("contract_no",retMap.get("contract_no").toString());
            json.put("customer_spec",retMap.get("customer_spec").toString());
            json.put("machining_contract_no",retMap.get("machining_contract_no").toString());
            json.put("od",retMap.get("od").toString());
            json.put("wt",retMap.get("wt").toString());
            json.put("lot_no",retMap.get("lot_no").toString());
            json.put("material_no",retMap.get("material_no").toString());
            json.put("steel_grade",retMap.get("steel_grade").toString());
            json.put("threading_type",retMap.get("threading_type").toString());
            json.put("coupling_type",retMap.get("coupling_type").toString());


            json.put("success",true);
            ResponseUtil.write(response, json);
            System.out.print("uploadContractList成功");
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
    public HashMap importExcelInfo( String fullfilename) throws Exception{

        HashMap retMap = new HashMap();//返回值



        List<List<Object>> listob = ExcelUtil.readFromFiletoList(fullfilename);
        //遍历listob数据，把数据放到List中

        List<Object> ob = listob.get(ExcelUtil.CONTRACT_NO_INDEX_i);
        if(ob!=null&&ExcelUtil.CONTRACT_NO_INDEX_j<=ob.size()-1) {
            String val=String.valueOf(ob.get(ExcelUtil.CONTRACT_NO_INDEX_j));
            retMap.put("contract_no", val);
            System.out.println("contract_no:"+val);
        }
        ob = listob.get(ExcelUtil.CUSTOMER_SPEC_INDEX_i);
        if(ob!=null&&ExcelUtil.CUSTOMER_SPEC_INDEX_j<=ob.size()-1) {
            String val=String.valueOf(ob.get(ExcelUtil.CUSTOMER_SPEC_INDEX_j));
            retMap.put("customer_spec", val);
            System.out.println("customer_spec:"+val);
        }

        ob = listob.get(ExcelUtil.MACHINING_CONTRACT_NO_INDEX_i);
        if(ob!=null&&ExcelUtil.MACHINING_CONTRACT_NO_INDEX_j<=ob.size()-1) {
            String val=String.valueOf(ob.get(ExcelUtil.MACHINING_CONTRACT_NO_INDEX_j));
            retMap.put("machining_contract_no", val);
            System.out.println("machining_contract_no:"+val);
        }


        ob = listob.get(ExcelUtil.OD_WT_INDEX_i);
        if(ob!=null&&ExcelUtil.OD_WT_INDEX_j<=ob.size()-1) {
            String val=String.valueOf(ob.get(ExcelUtil.OD_WT_INDEX_j));
            //od * wt mm 格式
            String od=val.substring(0,val.lastIndexOf('*'));
            String wt=val.substring(val.lastIndexOf('*')+1,val.lastIndexOf("mm"));
            od=od.trim();
            wt=wt.trim();

            retMap.put("od", od);
            retMap.put("wt", wt);
            System.out.println("od:"+od);
            System.out.println("wt:"+wt);

        }

        ob = listob.get(ExcelUtil.LOT_NO_INDEX_i);
        if(ob!=null&&ExcelUtil.LOT_NO_INDEX_j<=ob.size()-1) {
            String val=String.valueOf(ob.get(ExcelUtil.LOT_NO_INDEX_j));
            retMap.put("lot_no", val);
            System.out.println("lot_no:"+val);
        }

        ob = listob.get(ExcelUtil.MATERIAL_NO_INDEX_i);
        if(ob!=null&&ExcelUtil.MATERIAL_NO_INDEX_j<=ob.size()-1) {
            String val=String.valueOf(ob.get(ExcelUtil.MATERIAL_NO_INDEX_j));
            retMap.put("material_no", val);
            System.out.println("material_no:"+val);
        }

        ob = listob.get(ExcelUtil.STEEL_GRADE_INDEX_i);
        if(ob!=null&&ExcelUtil.STEEL_GRADE_INDEX_j<=ob.size()-1) {
            String val=String.valueOf(ob.get(ExcelUtil.STEEL_GRADE_INDEX_j));
            retMap.put("steel_grade", val);
            System.out.println("steel_grade:"+val);
        }

        ob = listob.get(ExcelUtil.THREADING_TYPE_INDEX_i);
        if(ob!=null&&ExcelUtil.THREADING_TYPE_INDEX_j<=ob.size()-1) {
            String val=String.valueOf(ob.get(ExcelUtil.THREADING_TYPE_INDEX_j));
            retMap.put("threading_type", val);
            System.out.println("threading_type:"+val);
        }

        ob = listob.get(ExcelUtil.COUPING_TYPE_INDEX_i);
        if(ob!=null&&ExcelUtil.COUPING_TYPE_INDEX_j<=ob.size()-1) {
            String val=String.valueOf(ob.get(ExcelUtil.COUPING_TYPE_INDEX_j));
            retMap.put("coupling_type", val);
            System.out.println("coupling_type:"+val);
        }



        return retMap;
    }
}
