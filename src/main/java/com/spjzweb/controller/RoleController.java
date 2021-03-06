package com.spjzweb.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spjzweb.dao.RoleDao;
import com.spjzweb.entity.Role;
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
@RequestMapping("/Role")
public class RoleController {
    @Autowired
    private RoleDao roleDao;
    /**
     * 分页获取角色列表
     * @param role_no(角色编号)
     * @param role_name(角色名称)
     * @param request
     * @return
     */
    @RequestMapping("getRoleByLike")
    @ResponseBody
    public String getRoleByLike(@RequestParam(value = "role_no",required = false)String role_no, @RequestParam(value = "role_name",required = false)String role_name, HttpServletRequest request){
        String page= request.getParameter("page");
        String rows= request.getParameter("rows");
        if(page==null){
            page="1";
        }
        if(rows==null){
            rows="20";
        }
        int start=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
        List<HashMap<String,Object>> list=roleDao.getAllByLike(role_no,role_name,start,Integer.parseInt(rows));
        int count=roleDao.getCountAllByLike(role_no,role_name);
        Map<String,Object> maps=new HashMap<String,Object>();
        maps.put("total",count);
        maps.put("rows",list);
        String mmp= JSONArray.toJSONString(maps);
        System.out.print("mmp:"+mmp);
        return mmp;

    }
    /**
     * 根据角色编号和名称获取所有角色信息
     * @param role_no(角色编号)
     * @param role_name(角色名称)
     * @param request
     * @return
     */
    @RequestMapping(value ="/getAllRoleByLike",produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getAllRoleByLike(@RequestParam(value = "role_no",required = false)String role_no, @RequestParam(value = "role_name",required = false)String role_name, HttpServletRequest request){
        List<HashMap<String,Object>> list=roleDao.getAllRoleByLike(role_no,role_name);
        String mmp= JSONArray.toJSONString(list);
        return mmp;
    }
    /**
     * 添加或修改角色信息
     * @param role(角色信息)
     * @param response
     * @return
     */
    @RequestMapping(value = "/saveRole")
    @ResponseBody
    public String saveRole(Role role, HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            int resTotal=0;
            if(role.getId()==0){
                //添加
                resTotal=roleDao.addRole(role);
            }else{
                //修改！
                resTotal=roleDao.updateRole(role);
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
    /**
     * hlparam(角色id集合,","分割)
     * @param hlparam
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delRole")
    public String delRole(@RequestParam(value = "hlparam")String hlparam,HttpServletResponse response)throws Exception{
        String[]idArr=hlparam.split(",");
        int resTotal=0;
        resTotal=roleDao.delRole(idArr);
        JSONObject json=new JSONObject();
        StringBuilder sbmessage = new StringBuilder();
        sbmessage.append("总共");
        sbmessage.append(Integer.toString(resTotal));
        sbmessage.append("项角色信息删除成功\n");
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
