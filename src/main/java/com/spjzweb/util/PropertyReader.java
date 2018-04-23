package com.spjzweb.util;
import java.io.InputStream;
import java.util.Properties;


//读取db.properties的配置信息





public class PropertyReader {
    static private String ftpServerAddress = null;

    static{
        loads();
    }
    synchronized static public void loads(){
        if(ftpServerAddress == null)
        {
            InputStream is = PropertyReader.class.getResourceAsStream("/db.properties");
            Properties dbProps = new Properties();
            try {
                dbProps.load(is);
                ftpServerAddress = dbProps.getProperty("ftpServerAddress");
            }
            catch (Exception e) {
                System.err.println("不能读取属性文件. " +
                        "请确保db.properties在CLASSPATH指定的路径中");
            }
        }
    }
    public static String getFtpServerAddress() {
        if(ftpServerAddress==null)
            loads();
        return ftpServerAddress;
    }

//    public static void main(String[] args) {
//
//        System.out.println("ftp:"+PropertyReader.getFtpServerAddress());
//    }

}

