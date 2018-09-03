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
    /**
     * 分页查询检验接收标准
     * @param thread_acceptance_criteria_no(标准编号)
     * @param od(外径)
     * @param wt(壁厚)
     * @param customer_spec(客户标准)
     * @param coupling_type(接箍类型)
     * @param threading_type(螺纹类型)
     * @param request
     * @return
     */
    @RequestMapping("/getThreadAcceptanceCriteriaAllByLike")
    @ResponseBody
    public String getThreadAcceptanceCriteriaAllByLike(@RequestParam(value = "thread_acceptance_criteria_no",required = false)String thread_acceptance_criteria_no,@RequestParam(value = "od",required = false)String od,@RequestParam(value = "wt",required = false)String wt,@RequestParam(value = "customer_spec",required = false)String customer_spec,@RequestParam(value = "coupling_type",required = false)String coupling_type,@RequestParam(value = "threading_type",required = false)String threading_type, HttpServletRequest request){
        String page= request.getParameter("page");
        String rows= request.getParameter("rows");
        if(page==null||page==""){
            page="0";
        }if(rows==null||rows==""){
            rows="20";
        }
        int start=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
        List<ThreadingAcceptanceCriteria>list=threadingAcceptanceCriteriaDao.getAllByLike(thread_acceptance_criteria_no,od,wt,customer_spec,coupling_type,threading_type,start,Integer.parseInt(rows));
        int count=threadingAcceptanceCriteriaDao.getCountAllByLike(thread_acceptance_criteria_no,od,wt,customer_spec,coupling_type,threading_type);
        Map<String,Object> maps=new HashMap<String,Object>();
        maps.put("total",count);
        maps.put("rows",list);
        String mmp= JSONArray.toJSONString(maps);
        return mmp;
    }
    /**
     * 添加或修改接收标准
     * @param threadingAcceptanceCriteria(接收标准信息)
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/saveThreadAcceptanceCriteria")
    @ResponseBody
    public String saveThreadAcceptanceCriteria(ThreadingAcceptanceCriteria threadingAcceptanceCriteria, HttpServletRequest request, HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            int resTotal=0;
            threadingAcceptanceCriteria.setLast_update_time(new Date());
            System.out.println("threadingAcceptanceCriteria.getId()="+threadingAcceptanceCriteria.getId());
            if(threadingAcceptanceCriteria.getId()==0){
                //添加
                threadingAcceptanceCriteria.setThread_acceptance_criteria_no(String.valueOf(System.currentTimeMillis()));
                resTotal=threadingAcceptanceCriteriaDao.addThreadingAcceptanceCriteria(threadingAcceptanceCriteria);
            }else{
                //修改！
                resTotal=threadingAcceptanceCriteriaDao.updateThreadingAcceptanceCriteria(threadingAcceptanceCriteria);
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
            json.put("message","保存失败:"+e.getMessage());
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
     * 删除标准
     * @param hlparam(标准id集合,","分割)
     * @param response
     * @return
     * @throws Exception
     */
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
    /**
     * 获取所有下拉接收标准
     * @param thread_acceptance_criteria_no(接收标准编号)
     * @param request
     * @return
     */
    @RequestMapping("/getAllDropDownAcceptanceCriteria")
    @ResponseBody
    public String getAllDropDownAcceptanceCriteria(@RequestParam(value = "thread_acceptance_criteria_no",required = false)String thread_acceptance_criteria_no,HttpServletRequest request){
        List<ThreadingAcceptanceCriteria>list=threadingAcceptanceCriteriaDao.getAllDropDown();
        List<ComboxItem> colist=new ArrayList<ComboxItem>();
        for(int i=0;i<list.size();i++){
            ComboxItem citem= new ComboxItem();
            ThreadingAcceptanceCriteria ps=((ThreadingAcceptanceCriteria)list.get(i));
            citem.id=ps.getThread_acceptance_criteria_no();
            //接收标准编号显示由(外径、壁厚、螺纹类型、客户标准、接箍类型组成)
            citem.text=ps.getThread_acceptance_criteria_no()+"("+ps.getOd()+"*"+ps.getWt()+"/"+ps.getThreading_type()+"/"+ps.getCustomer_spec()+"/"+ps.getCoupling_type()+")";
            colist.add(citem);
        }
        String map= JSONObject.toJSONString(colist);
        return map;
    }
}
