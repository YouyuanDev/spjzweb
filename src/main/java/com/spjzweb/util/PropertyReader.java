package com.spjzweb.util;
import java.io.InputStream;
import java.util.Properties;


//读取db.properties的配置信息





public class PropertyReader {
    static private String videoAddress = null;

    static{
        loads();
    }
    synchronized static public void loads(){
        if(videoAddress == null)
        {
            InputStream is = PropertyReader.class.getResourceAsStream("/db.properties");
            Properties dbProps = new Properties();
            try {
                dbProps.load(is);
                videoAddress = dbProps.getProperty("videoAddress");
            }
            catch (Exception e) {
                System.err.println("不能读取属性文件. " +
                        "请确保db.properties在CLASSPATH指定的路径中");
            }
        }
    }
    public static String getVideoAddress() {
        if(videoAddress==null)
            loads();
        return videoAddress;
    }

//    public static void main(String[] args) {
//
//        System.out.println("ftp:"+PropertyReader.getFtpServerAddress());
//    }

}

