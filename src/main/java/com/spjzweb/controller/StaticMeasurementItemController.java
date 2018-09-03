package com.spjzweb.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spjzweb.dao.ContractInfoDao;
import com.spjzweb.dao.ItemRecordDao;
import com.spjzweb.dao.StaticMeasurementItemDao;
import com.spjzweb.dao.ThreadInspectionRecordDao;
import com.spjzweb.entity.ContractInfo;
import com.spjzweb.entity.StaticMeasurementItem;
import com.spjzweb.entity.ThreadInspectionRecord;
import com.spjzweb.util.ComboxItem;
import com.spjzweb.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/StaticMeasure")
public class StaticMeasurementItemController {
    @Autowired
    private StaticMeasurementItemDao staticMeasurementItemDao;
    @Autowired
    private ContractInfoDao contractInfoDao;
    @Autowired
    private ThreadInspectionRecordDao threadInspectionRecordDao;
    @Autowired
    private ItemRecordDao itemRecordDao;
    /**
     * 分页查询静态测量项
     * @param measure_item_code(测量项编码)
     * @param measure_item_name(测量项名称)
     * @param request
     * @return
     */
    @RequestMapping(value = "getStaticMeasureItemAllByLike",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getStaticMeasureItemAllByLike(@RequestParam(value = "measure_item_code",required = false)String measure_item_code,@RequestParam(value = "measure_item_name",required = false)String measure_item_name, HttpServletRequest request){
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
        return mmp;
    }
    /**
     * 添加或修改静态测量项
     * @param staticMeasurementItem(静态测量项)
     * @param response
     * @return
     */
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
                    resTotal=staticMeasurementItemDao.addStaticMeasurementItem(staticMeasurementItem);
                }else{
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
    /**
     * 删除静态测量项
     * @param hlparam(测量项id集合,","分割)
     * @param response
     * @return
     * @throws Exception
     */
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
    /**
     * 获取所有的静态测量项的编码和名字
     * @param response
     * @return
     */
    @RequestMapping(value = "/getAllDropdownStaticItem",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getAllDropdownStaticItem(HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
           List<HashMap<String,Object>>hsList=staticMeasurementItemDao.getAllDropdownStaticItem();
           String mmp= JSONArray.toJSONString(hsList);
            json.put("promptkey","success");
            json.put("promptValue",mmp);
        }catch (Exception e){
            json.put("promptkey","error");
            json.put("promptValue",e.getMessage());
        }finally {
            try{
                ResponseUtil.write(response,json);
            }catch (Exception e){

            }
        }
        return null;
    }
    /**
     * 根据合同编号获取使用测量工具和标准等信息(Winform使用)
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getMeasureDataByContractNoOfWinform")
    @ResponseBody
    public String getMeasureDataByContractNoOfWinform(HttpServletRequest request,HttpServletResponse response)throws  Exception{
        StringBuilder sb=new StringBuilder();
        JSONObject jsonReturn=new JSONObject();
        try{
            BufferedReader reader=request.getReader();
            String input=null;
            while ((input=reader.readLine())!=null){
                sb.append(input);
            }
            JSONObject json=JSONObject.parseObject(sb.toString());
            String contract_no=json.getString("contract_no");
            if(contract_no!=null&&!contract_no.equals("")){
                //根据合同编号获取合同信息
                ContractInfo contractInfo=contractInfoDao.getContractInfoByNo(contract_no);
                List<HashMap<String,Object>>dynamicAndStaticData=null;
                //根据接收标准编号接收动态、静态信息
                if(contractInfo!=null){
                   String acceptance_no=contractInfo.getThread_acceptance_criteria_no();
                   if(acceptance_no!=null&&!acceptance_no.equals("")){
                       dynamicAndStaticData=staticMeasurementItemDao.getDynamicAndStaticDataByAcceptanceNo(acceptance_no);
                   }
                }
                HashMap<String,Object>returenData=new HashMap<>();
                returenData.put("contractInfo",contractInfo);
                returenData.put("measureInfo",dynamicAndStaticData);
                jsonReturn.put("rowsData",returenData);
            }else{
                jsonReturn.put("rowsData","fail");
            }
        }catch (Exception e){
            jsonReturn.put("rowsData","fail");
            e.printStackTrace();
        }finally {
            ResponseUtil.write(response,jsonReturn.toString());
        }
        return  null;
    }
    /**
     * 根据接箍检验编号获取使用测量工具和标准等信息(Winform使用)
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getMeasureDataByInspectionNoOfWinform")
    @ResponseBody
    public String getMeasureDataByInspectionNoOfWinform(HttpServletRequest request,HttpServletResponse response)throws  Exception{
        StringBuilder sb=new StringBuilder();
        JSONObject jsonReturn=new JSONObject();
        try{
            BufferedReader reader=request.getReader();
            String input=null;
            while ((input=reader.readLine())!=null){
                sb.append(input);
            }
            JSONObject json=JSONObject.parseObject(sb.toString());
            String thread_inspection_record_code=json.getString("thread_inspection_record_code");
            if(thread_inspection_record_code!=null&&!thread_inspection_record_code.equals("")){
                //根据接箍编号找到对应的合同编号
                ThreadInspectionRecord entity=threadInspectionRecordDao.getThreadInspectionRecordByNo(thread_inspection_record_code);
                String  contract_no=null;
                if(entity!=null)
                {
                    contract_no=entity.getContract_no();
                }
                //根据合同编号获取合同信息
                ContractInfo contractInfo=contractInfoDao.getContractInfoByNo(contract_no);
                List<HashMap<String,Object>>dynamicAndStaticData=null;
                //根据接收标准编号接收动态、静态信息
                if(contractInfo!=null){
                    String acceptance_no=contractInfo.getThread_acceptance_criteria_no();
                    if(acceptance_no!=null&&!acceptance_no.equals("")){
                        dynamicAndStaticData=staticMeasurementItemDao.getDynamicAndStaticDataByAcceptanceNo(acceptance_no);
                    }
                }
                //查询检验数据
                List<HashMap<String,Object>>inspectionData=itemRecordDao.getItemRecordByInspectionRecordCode(thread_inspection_record_code);
                HashMap<String,Object>returenData=new HashMap<>();
                returenData.put("contractInfo",contractInfo);
                returenData.put("measureInfo",dynamicAndStaticData);
                returenData.put("inspectionData",inspectionData);
                jsonReturn.put("rowsData",returenData);
            }else{
                jsonReturn.put("rowsData","fail");
            }
        }catch (Exception e){
            jsonReturn.put("rowsData","fail");
            e.printStackTrace();
        }finally {
            ResponseUtil.write(response,jsonReturn.toString());
        }
        return  null;
    }
}

