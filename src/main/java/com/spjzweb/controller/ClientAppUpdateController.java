package com.spjzweb.controller;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.spjzweb.util.ResponseUtil;
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
@RequestMapping("/ClientAppUpdate")
public class ClientAppUpdateController {

    public static boolean isServerTomcat=true; //是否为Tomcat部署

    //客户端程序包上传
    @RequestMapping(value = "/uploadClientAppPackage",method = RequestMethod.POST)
    public String uploadClientAppPackage(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            //String saveDirectory = request.getSession().getServletContext().getRealPath("/upload/clientapp");

            String saveDirectory = request.getSession().getServletContext().getRealPath("/");
            saveDirectory=saveDirectory.substring(0,saveDirectory.lastIndexOf('/'));
            System.out.println("saveDirector1="+saveDirectory);
            if(isServerTomcat){
                saveDirectory=saveDirectory.substring(0,saveDirectory.lastIndexOf('/'));
            }
            saveDirectory=saveDirectory+"/upload/clientapp";

            System.out.println("saveDirector2="+saveDirectory);

            File uploadPath = new File(saveDirectory);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }
            //FileRenameUtil util = new FileRenameUtil();
            System.out.println("saveDirectory="+saveDirectory);
            MultipartRequest multi = new MultipartRequest(request, saveDirectory, 100* 1024 * 1024, "UTF-8");
            Enumeration files = multi.getFileNames();
            String newName = "";
            File file=null;
            //HashMap retMap=null;
            if (files.hasMoreElements()) {
                String name = (String) files.nextElement();

                file = multi.getFile(name);
                if (file != null) {
                    newName = file.getName();

                    //处理excel文件
                    //retMap =importExcelInfo(saveDirectory+"/"+newName);
                }
            }

            JSONObject json = new JSONObject();
            json.put("filename", newName);
            json.put("filesize", file.length());
            json.put("success",true);
            ResponseUtil.write(response, json);
            System.out.print("uploadClientAppPackage成功");
            System.out.println("saveDirectory File="+saveDirectory+"/"+newName);
            System.out.println("file.length()="+file.length());
        } catch (Exception e) {
            System.err.println("Exception=" + e.getMessage().toString());
            System.out.println("Exception=" + e.getMessage().toString());
            e.printStackTrace();
            JSONObject json = new JSONObject();
            json.put("success",false);
            ResponseUtil.write(response, json);
        }
        return null;
    }



    //客户端程序包配置文件上传
    @RequestMapping(value = "/uploadClientAppAutoUpdaterXML",method = RequestMethod.POST)
    public String uploadClientAppAutoUpdaterXML(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            //String saveDirectory = request.getSession().getServletContext().getRealPath("/upload/clientapp");
            String saveDirectory = request.getSession().getServletContext().getRealPath("/");
            saveDirectory=saveDirectory.substring(0,saveDirectory.lastIndexOf('/'));

            if(isServerTomcat){
                saveDirectory=saveDirectory.substring(0,saveDirectory.lastIndexOf('/'));
            }
            saveDirectory=saveDirectory+"/upload/clientapp";

            System.out.println("saveDirector1="+saveDirectory);


            File uploadPath = new File(saveDirectory);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }
            //FileRenameUtil util = new FileRenameUtil();
            System.out.println("saveDirectory="+saveDirectory);
            MultipartRequest multi = new MultipartRequest(request, saveDirectory, 100* 1024 * 1024, "UTF-8");
            Enumeration files = multi.getFileNames();
            String newName = "";
            File file=null;
            //HashMap retMap=null;
            if (files.hasMoreElements()) {
                String name = (String) files.nextElement();

                file = multi.getFile(name);
                if (file != null) {
                    newName = file.getName();

                    //处理excel文件
                    //retMap =importExcelInfo(saveDirectory+"/"+newName);
                }
            }

            JSONObject json = new JSONObject();
            json.put("filename", newName);
            json.put("filesize", file.length());
            json.put("success",true);
            ResponseUtil.write(response, json);
            System.out.print("uploadClientAppAutoUpdaterXML成功");
            System.out.println("saveDirectory File="+saveDirectory+"/"+newName);
            System.out.println("file.length()="+file.length());
        } catch (Exception e) {
            System.err.println("Exception=" + e.getMessage().toString());
            System.out.println("Exception=" + e.getMessage().toString());
            e.printStackTrace();
            JSONObject json = new JSONObject();
            json.put("success",false);
            ResponseUtil.write(response, json);
        }
        return null;
    }
}
