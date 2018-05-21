package com.spjzweb.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spjzweb.dao.ThreadingAcceptanceCriteriaDao;
import com.spjzweb.entity.ThreadingAcceptanceCriteria;
import com.spjzweb.util.ComboxItem;
import com.spjzweb.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/AcceptanceCriteriaOperation")
public class ThreadAcceptanceCriteriaController {
    @Autowired
    ThreadingAcceptanceCriteriaDao threadingAcceptanceCriteriaDao;

    @RequestMapping("/getThreadAcceptanceCriteriaAllByLike")
    @ResponseBody
    public String getThreadAcceptanceCriteriaAllByLike(@RequestParam(value = "thread_acceptance_criteria_no",required = false)String thread_acceptance_criteria_no, HttpServletRequest request){
        String page= request.getParameter("page");
        String rows= request.getParameter("rows");
        if(page==null||page==""){
            page="0";
        }if(rows==null||rows==""){
            rows="20";
        }
        int start=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
        List<ThreadingAcceptanceCriteria>list=threadingAcceptanceCriteriaDao.getAllByLike(thread_acceptance_criteria_no,start,Integer.parseInt(rows));
        int count=threadingAcceptanceCriteriaDao.getCountAllByLike(thread_acceptance_criteria_no);
        Map<String,Object> maps=new HashMap<String,Object>();
        maps.put("total",count);
        maps.put("rows",list);
        String mmp= JSONArray.toJSONString(maps);
        return mmp;
    }
    @RequestMapping("/saveThreadAcceptanceCriteria")
    @ResponseBody
    public String saveThreadAcceptanceCriteria(ThreadingAcceptanceCriteria  threadingAcceptanceCriteria, HttpServletRequest request, HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            int resTotal=0;
            threadingAcceptanceCriteria.setLast_update_time(new Date());
            if(threadingAcceptanceCriteria.getId()==0){
                //添加
                resTotal=threadingAcceptanceCriteriaDao.addThreadingAcceptanceCriteria(threadingAcceptanceCriteria);
            }else{
                //修改！
                resTotal=threadingAcceptanceCriteriaDao.updateThreadingAcceptanceCriteria(threadingAcceptanceCriteria);
            }
            if(resTotal>0){
                json.put("promptkey","success");
            }else{
                json.put("promptkey","fail");
            }



        }catch (Exception e){
            e.printStackTrace();
            json.put("promptkey","fail");
        }finally {
            try {
                ResponseUtil.write(response, json);
            }catch  (Exception e) {
                e.printStackTrace();
                json.put("promptkey","fail");
            }
        }
        return null;
    }
//
    //删除标准
    @RequestMapping("/delThreadAcceptanceCriteria")
    public String delThreadAcceptanceCriteria(@RequestParam(value = "hlparam")String hlparam,HttpServletResponse response)throws Exception{
        String[]idArr=hlparam.split(",");
        int resTotal=0;
        resTotal=threadingAcceptanceCriteriaDao.delThreadingAcceptanceCriteria(idArr);
        JSONObject json=new JSONObject();
        StringBuilder sbmessage = new StringBuilder();
        sbmessage.append("总共");
        sbmessage.append(Integer.toString(resTotal));
        sbmessage.append("条接收标准删除成功\n");
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
//
    //获取所有下拉接收标准
    @RequestMapping("/getAllDropDownAcceptanceCriteria")
    @ResponseBody
    public String getAllDropDownAcceptanceCriteria(@RequestParam(value = "thread_acceptance_criteria_no",required = false)String thread_acceptance_criteria_no,HttpServletRequest request){
        List<ThreadingAcceptanceCriteria>list=threadingAcceptanceCriteriaDao.getAllDropDown();
        List<ComboxItem> colist=new ArrayList<ComboxItem>();
        for(int i=0;i<list.size();i++){
            ComboxItem citem= new ComboxItem();
            ThreadingAcceptanceCriteria ps=((ThreadingAcceptanceCriteria)list.get(i));
            citem.id=ps.getThread_acceptance_criteria_no();
            citem.text=ps.getThread_acceptance_criteria_no();
            colist.add(citem);
        }
        String map= JSONObject.toJSONString(colist);
        return map;
    }
//    //windform获取所有下拉接收标准
//    @RequestMapping("/getAllDropDownAcceptanceCriteriaByWinform")
//    @ResponseBody
//    public String getAllDropDownAcceptanceCriteriaByWinform(HttpServletRequest request,HttpServletResponse response)throws  Exception{
//        List<ComboxItem> colist=new ArrayList<ComboxItem>();
//        JSONObject jsonReturn=new JSONObject();
//        try{
//            List<ThreadAcceptanceCriteria>list=threadAcceptanceCriteriaDao.getAllDropDownAcceptanceCriteria(null);
//            for(int i=0;i<list.size();i++){
//                ComboxItem citem= new ComboxItem();
//                ThreadAcceptanceCriteria ps=((ThreadAcceptanceCriteria)list.get(i));
//                citem.id=ps.getThread_acceptance_criteria_no();
//                citem.text=ps.getThread_acceptance_criteria_no();
//                colist.add(citem);
//            }
//            jsonReturn.put("rowsData",colist);
//
//        }catch (Exception e){
//            jsonReturn.put("rowsData",colist);
//            e.printStackTrace();
//        }finally {
//            ResponseUtil.write(response,jsonReturn.toString());
//        }
//        return  null;
//    }
//    //根据接收标准编号查询标准信息
//    @RequestMapping("/getAcceptanceCriteriaByNo")
//    @ResponseBody
//    public String getAcceptanceCriteriaByNo(HttpServletRequest request){
//        String thread_acceptance_criteria_no=request.getParameter("thread_acceptance_criteria_no");
//        String map=null;
//        if(thread_acceptance_criteria_no!=null&&thread_acceptance_criteria_no!=""){
//            List<ThreadAcceptanceCriteria> criteriaList=threadAcceptanceCriteriaDao.getAllDropDownAcceptanceCriteria(thread_acceptance_criteria_no);
//            if(criteriaList.size()>0){
//                ThreadAcceptanceCriteria criteria=criteriaList.get(0);
//                if(criteria!=null){
//                    map= JSONObject.toJSONString(criteria);
//                }
//            }
//        }
//        return map;
//    }
}
