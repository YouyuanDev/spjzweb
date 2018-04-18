package com.spjzweb.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spjzweb.dao.ContractInfoDao;
import com.spjzweb.entity.ContractInfo;
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
@RequestMapping("/Contract")
public class ContractController {
    @Autowired
    private ContractInfoDao contractInfoDao;
    //搜索
    @RequestMapping(value = "getContractByLike",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getContractByLike(@RequestParam(value = "contract_no",required = false)String contract_no,HttpServletRequest request){
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
}
