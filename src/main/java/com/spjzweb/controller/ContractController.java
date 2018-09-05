package com.spjzweb.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spjzweb.dao.ContractInfoDao;
import com.spjzweb.entity.ContractInfo;
import com.spjzweb.util.ComboxItem;
import com.spjzweb.util.ExcelUtil;
import com.spjzweb.util.ResponseUtil;
import org.apache.ibatis.jdbc.Null;
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
    /**
     * 合同分页查询
     * @param contract_no(合同编号)
     * @param customer_spec(客户标准)
     * @param od(外径)
     * @param wt(壁厚)
     * @param threading_type(螺纹类型)
     * @param request
     * @return
     */
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
        return mmp;
    }
    /**
     * 合同信息添加和修改
     * @param contractInfo(合同信息)
     * @param response
     * @return
     */
    @RequestMapping(value = "/saveContract")
    @ResponseBody
    public String saveContract(ContractInfo contractInfo, HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            int resTotal=0;
            //先判断是否存在相通的合同编号
            List<ContractInfo>contractInfoList=null;
            if(contractInfo.getId()==0){
                contractInfoList=contractInfoDao.getContractInfoByContractNoOfAdd(contractInfo.getContract_no());
            }else{
                contractInfoList=contractInfoDao.getContractInfoByContractNoOfEdit(contractInfo.getContract_no());
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
    /**
     * 删除合同信息
     * @param hlparam(合同信息id集合,","分割)
     * @param response
     * @return
     * @throws Exception
     */
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
    /**
     * Excel合同文件上传
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadContractList",method = RequestMethod.POST)
    public String uploadContractList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            String saveDirectory = request.getSession().getServletContext().getRealPath("/");
            if(saveDirectory.lastIndexOf('/')==-1){
                saveDirectory=saveDirectory.replace('\\','/');
            }
            saveDirectory=saveDirectory.substring(0,saveDirectory.lastIndexOf('/'));
            if(ClientAppUpdateController.isServerTomcat){
                saveDirectory=saveDirectory.substring(0,saveDirectory.lastIndexOf('/'));
            }
            saveDirectory=saveDirectory+"/upload/contracts";
            File uploadPath = new File(saveDirectory);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }
            MultipartRequest multi = new MultipartRequest(request, saveDirectory, 100* 1024 * 1024, "UTF-8");
            Enumeration files = multi.getFileNames();
            String newName = "";
            File file=null;
            HashMap retMap=null;
            if (files.hasMoreElements()) {
                String name = (String) files.nextElement();
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
            System.out.print("Excel合同文件录入成功");
            System.out.println("Excel合同文件保存路径："+saveDirectory+"/"+newName);
            System.out.println("Excel合同文件大小："+file.length());
        } catch (Exception e) {
            System.out.println("Excel合同文件录入异常，异常信息：" + e.getMessage().toString());
            e.printStackTrace();
            JSONObject json = new JSONObject();
            json.put("success",false);
            ResponseUtil.write(response, json);
        }
        return null;
    }
    /**
     * Excel合同文件数据导入
     * @param fullfilename(Excel名称)
     * @return
     * @throws Exception
     */
    public HashMap importExcelInfo( String fullfilename) throws Exception{
        HashMap retMap = new HashMap();//返回值
        try{
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
                int index_start=val.lastIndexOf('*');
                int index_mm=val.lastIndexOf("mm");
                String od="",wt="";
                if(index_start!=-1){
                     od=val.substring(0,index_start);
                     if(index_mm!=-1)
                       wt=val.substring(index_start+1,index_mm);
                     else
                       wt=val.substring(index_start+1);
                    od=od.trim();
                    wt=wt.trim();
                }
                retMap.put("od", od);
                retMap.put("wt", wt);
                System.out.println("od:"+od+"，wt:"+wt);
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
            System.out.println("Excel合同文件导入成功！");
        }catch (Exception e){
            System.out.println("Excel合同文件录入异常，异常信息：" + e.getMessage().toString());
            e.printStackTrace();
        }finally {
            return retMap;
        }
    }
    /**
     * 根据接收标准编号获取所有接收标准(下拉框使用)
     * @param thread_acceptance_criteria_no(接收标准编号)
     * @param request
     * @return
     */
    @RequestMapping("/getAllDropDownContractInfo")
    @ResponseBody
    public String getAllDropDownContractInfo(@RequestParam(value = "thread_acceptance_criteria_no",required = false)String thread_acceptance_criteria_no,HttpServletRequest request){
        List<ContractInfo>list=contractInfoDao.getAllDropDownContractNoOfWinform();
        List<ComboxItem> colist=new ArrayList<ComboxItem>();
        for(int i=0;i<list.size();i++){
            ComboxItem citem= new ComboxItem();
            ContractInfo ps=((ContractInfo)list.get(i));
            citem.id=ps.getContract_no();
            citem.text=ps.getContract_no();
            colist.add(citem);
        }
        String map= JSONObject.toJSONString(colist);
        return map;
    }
    /**
     * 获取所有下拉合同编号(客户端使用)
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAllDropDownContractNoOfWinform")
    @ResponseBody
    public String getAllDropDownContractNoOfWinform(HttpServletRequest request,HttpServletResponse response)throws  Exception{
        List<ComboxItem> colist=new ArrayList<ComboxItem>();
        JSONObject jsonReturn=new JSONObject();
        try{
            List<ContractInfo>list=contractInfoDao.getAllDropDownContractNoOfWinform();
            for(int i=0;i<list.size();i++){
                ComboxItem citem= new ComboxItem();
                ContractInfo ps=((ContractInfo)list.get(i));
                citem.id=ps.getContract_no();
                citem.text=ps.getContract_no();
                colist.add(citem);
            }
            jsonReturn.put("rowsData",colist);

        }catch (Exception e){
            jsonReturn.put("rowsData",colist);
            e.printStackTrace();
        }finally {
            ResponseUtil.write(response,jsonReturn.toString());
        }
        return  null;
    }
    /**
     * 获取所有下拉合同信息用于客户端搜索(客户端使用)
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAllContractNoOfWinform")
    @ResponseBody
    public String getAllContractNoOfWinform(HttpServletRequest request,HttpServletResponse response)throws  Exception{
        JSONObject jsonReturn=new JSONObject();
        List<ContractInfo>list=new ArrayList<>();
        try{
            list=contractInfoDao.getAllDropDownContractNoOfWinform();
            jsonReturn.put("rowsData",list);
        }catch (Exception e){
            jsonReturn.put("rowsData",list);
            e.printStackTrace();
        }finally {
            ResponseUtil.write(response,jsonReturn.toString());
        }
        return  null;
    }
}
