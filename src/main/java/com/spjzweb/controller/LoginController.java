package com.spjzweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.spjzweb.dao.ClientAuthDao;
import com.spjzweb.dao.FunctionDao;
import com.spjzweb.dao.PersonDao;
import com.spjzweb.dao.RoleDao;
import com.spjzweb.entity.ClientAuth;
import com.spjzweb.entity.Function;
import com.spjzweb.entity.Person;
import com.spjzweb.entity.Role;
import com.spjzweb.util.AESUtil;
import com.spjzweb.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


//import org.apache.shiro.crypto.hash.Md5Hash;
//import org.apache.shiro.crypto.hash.SimpleHash;


@Controller
@RequestMapping("/Login")
public class LoginController {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private FunctionDao functionDao;
    @Autowired
    private ClientAuthDao clientAuthDao;
    /**
     * 登录验证
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/commitLogin")
    @ResponseBody
    public String commitLogin(HttpServletRequest request,HttpServletResponse response){
        JSONObject json=new JSONObject();
        String employee_no= request.getParameter("employee_no");
        String ppassword= request.getParameter("ppassword");
        String verificationCode= request.getParameter("verification_code");
        try{
            int resTotal=0;
            resTotal= personDao.confirmPersonByEmployeeNoPassword(employee_no,ppassword);
            if(resTotal>0){
                HttpSession session = request.getSession();
                //把用户数据保存在session域对象中
                session.setAttribute("userSession", employee_no);
                //设置权限
                HashMap<String,Object> functionMap=new HashMap<String,Object>();
                //这里读取数据库设置所有权限
                if(employee_no!=null) {
                    List<Person> lt=personDao.getPersonByEmployeeNo(employee_no);
                    if(lt.size()>0) {
                        Person person=lt.get(0);
                        String role_no_list=person.getRole_no_list();
                        if(role_no_list!=null&&!role_no_list.equals("")){
                            role_no_list=role_no_list.replace(',',';');
                            String[] roles= role_no_list.split(";");
                            for(int i=0;i<roles.length;i++){
                                List<Role> rolelt=roleDao.getRoleByRoleNo(roles[i]);
                                //System.out.println("role ="+roles[i]);
                                if(rolelt.size()>0) {
                                    Role role=rolelt.get(0);
                                    String functionlist = role.getFunction_no_list();
                                    if(functionlist!=null&&!functionlist.equals("")){
                                        functionlist=functionlist.replace(',',';');
                                        String[] func_no_s=functionlist.split(";");
                                        for(int j=0;j<func_no_s.length;j++) {
                                            List<Function> funlst=functionDao.getFunctionByFunctionNo(func_no_s[j]);
                                            if(funlst.size()>0){
                                                //得到function
                                                Function f=funlst.get(0);
                                                String function_no=f.getFunction_no();
                                                String uri=f.getUri();
                                                functionMap.put(function_no,"1");
                                                functionMap.put(uri,"1");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //更新验证码
                Format fo = new SimpleDateFormat("yyyyMMdd");
                Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
                Date tomorrow = c.getTime();
                String newkey=fo.format(tomorrow)+"00000000";
                String key2=AESUtil.KEY2;
                List<ClientAuth> res=clientAuthDao.getAllByLike(null,null);
                for(int i=0;i<res.size();i++){
                    int id=  res.get(i).getId();
                    String code1=  res.get(i).getEncrypt_code();
                    String code2= res.get(i).getEncrypt_code2();
                    System.out.println("code1="+code1);
                    System.out.println("code2="+code2);
                    if(code2!=null&&!code2.equals("")){
                        String keyforc1 = AESUtil.aesDecrypt(code2,key2);
                        if(keyforc1==null||keyforc1.equals("")){
                            clientAuthDao.delClientAuth(id);
                            continue;
                        }
                        String orgcode1=AESUtil.aesDecrypt(code1,keyforc1 );
                        if(orgcode1==null||orgcode1.equals("")){
                            clientAuthDao.delClientAuth(id);
                            continue;
                        }
                        String newCode1inDatabase= AESUtil.aesEncrypt(orgcode1,newkey );
                        String newCode2inDatabase=AESUtil.aesEncrypt(newkey,key2 );
                        //更新
                        ClientAuth ca=new ClientAuth();
                        ca.setId(res.get(i).getId());
                        ca.setEncrypt_code(newCode1inDatabase);
                        ca.setEncrypt_code2(newCode2inDatabase);
                        System.out.println("update code1="+newCode1inDatabase);
                        System.out.println("update code2="+newCode2inDatabase);
                        clientAuthDao.updateClientAuth(ca);

                    }
                }
                session.setAttribute("userfunctionMap", functionMap);
                //跳转到用户主页
                json.put("success",true);
            }else{
                json.put("success",false);
                json.put("msg","用户名或密码错误");
            }
            ResponseUtil.write(response,json);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 客户端登录(winform使用)
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/userLoginOfWinform")
    @ResponseBody
    public String userLoginOfWinform(HttpServletRequest request,HttpServletResponse response){
        JSONObject jsonReturn=new JSONObject();
        try{
            JSONObject jsonValid=new JSONObject();
            boolean illegal=true;
            Format fo = new SimpleDateFormat("yyyyMMdd");
            Date today = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(today);
            c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
            Date tomorrow = c.getTime();
            String newkey=fo.format(tomorrow)+"00000000";
            String key2=AESUtil.KEY2;
            //更新验证码
            List<ClientAuth> res=clientAuthDao.getAllByLike(null,null);
            for(int i=0;i<res.size();i++){
                String code1=  res.get(i).getEncrypt_code();
                String code2= res.get(i).getEncrypt_code2();
                System.out.println("getEncrypt_code="+code1);
                System.out.println("getEncrypt_code2="+code2);
                if(code2!=null&&!code2.equals("")){
                    String keyforc1 = AESUtil.aesDecrypt(code2,key2);
                    System.out.println("aesDecrypt(code2,key2)="+keyforc1);
                    String orgcode1=AESUtil.aesDecrypt(code1,keyforc1 );
                    String newCode1inDatabase= AESUtil.aesEncrypt(orgcode1,newkey );
                    String newCode2inDatabase=AESUtil.aesEncrypt(newkey,key2 );
                    //更新
                    ClientAuth ca=new ClientAuth();
                    ca.setId(res.get(i).getId());
                    ca.setEncrypt_code(newCode1inDatabase);
                    ca.setEncrypt_code2(newCode2inDatabase);
                    System.out.println("aesEncrypt(orgcode1,newkey )="+newCode1inDatabase);
                    System.out.println("aesEncrypt(newkey,key2 )="+newCode2inDatabase);
                    clientAuthDao.updateClientAuth(ca);
                }
            }
            //验证登录
            StringBuilder sb=new StringBuilder();
            BufferedReader reader=request.getReader();
            String input=null;
            while ((input=reader.readLine())!=null){
                sb.append(input);
            }
            JSONObject json=JSONObject.parseObject(sb.toString());
            String employee_no=null;
            String ppassword=null;
            Person person=null;
            String verificationCode= null;
            if(json!=null) {
                employee_no=json.getString("employee_no");
                ppassword=json.getString("ppassword");
                verificationCode=json.getString("verification_code");
            }
            if(verificationCode!=null){
                System.out.println("verificationCode="+verificationCode);
                String encryptData = verificationCode;
                String encryptData2 = AESUtil.aesEncrypt(newkey,key2 );
                List<ClientAuth> lt=clientAuthDao.getAllByLike(encryptData,encryptData2);
                if(lt.size()>0) {
                    System.out.println("Client verified!" + encryptData+encryptData2);
                    String ed2=(String)lt.get(0).getEncrypt_code2();
                    String k=AESUtil.aesDecrypt(ed2,key2 );
                    String decryptData = AESUtil.aesDecrypt(encryptData,k);
                    System.out.println("decryptData=" + decryptData);
                    illegal=false;
                }
            }
            if(illegal) {
                jsonReturn.put("success", false);
                jsonReturn.put("msg", "系统错误");
                jsonReturn.put("rowsData",null);
                ResponseUtil.write(response, jsonReturn);
                return null;
            }
            //验证登录
            if(json!=null){
                if(employee_no!=null&&employee_no!=""&&ppassword!=null&&ppassword!=""){
                     person=personDao.userLoginOfWinform(employee_no,ppassword);
                }
            }
            if(person!=null){
                jsonReturn.put("success", true);
                jsonReturn.put("msg", "登录成功");
                jsonReturn.put("rowsData",person);
            }else{
                jsonReturn.put("success", false);
                jsonReturn.put("msg", "用户名密码错误");
                jsonReturn.put("rowsData",null);
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
    /**
     * 登出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/Logout")
    @ResponseBody
    public String Logout(HttpServletRequest request,HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            HttpSession session = request.getSession();
            //把用户数据保存在session域对象中
            //session.removeAttribute("userSession");
            //session.removeAttribute("userfunctionMap");
            session.invalidate();
            //跳转到登录页面
            json.put("success",true);
            json.put("msg","登出成功");
            ResponseUtil.write(response,json);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 添加客户端登录验证信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("registKey")
    @ResponseBody
    public String registKey(HttpServletRequest request,HttpServletResponse response){
        JSONObject json=new JSONObject();
        try{
            String key1=request.getParameter("key1");
            String key2=request.getParameter("key2");
            System.out.println(key1+":"+key2);
            if(key1!=null&&!key1.equals("")&&key2!=null&&!key2.equals("")){
                ClientAuth ca=new ClientAuth();
                ca.setId(0);
                ca.setEncrypt_code(key1);
                ca.setEncrypt_code2(key2);
                int ret=clientAuthDao.addClientAuth(ca);
                if(ret>0){
                    json.put("success",true);
                    json.put("msg","注册成功");
                }else{
                    json.put("success",false);
                    json.put("msg","注册失败");
                }
            }else{
                json.put("success",false);
                json.put("msg","注册失败");
            }
            ResponseUtil.write(response,json);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

        }
}
