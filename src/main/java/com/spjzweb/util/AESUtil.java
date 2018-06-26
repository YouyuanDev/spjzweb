package com.spjzweb.util;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;

@SuppressWarnings("restriction")
public class AESUtil {

    public static String KEY2="SPJZWEB000000000";

    public static String aesEncrypt(String str, String key) {
        byte[] bytes=null;
        try {

            if (str == null || key == null) return null;
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
            bytes = cipher.doFinal(str.getBytes("utf-8"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return new BASE64Encoder().encode(bytes);
        }

    }

    public static String aesDecrypt(String str, String key){
        byte[] bytes=null;

        try {
            if (str == null || key == null) return null;
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
            bytes = new BASE64Decoder().decodeBuffer(str);
            bytes = cipher.doFinal(bytes);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if(bytes!=null)
                    return new String(bytes, "utf-8");
                else
                    return "";

            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

        return "";
    }


    public static void main(String arg[])
    {
        String Data="BC7574E1A129";

        //String Data="8CEC4B40200E";
        String key1="2018052300000000";
        String key2="SPJZWEB000000000";

        try {
            String encryptCode1=AESUtil.aesEncrypt(Data,key1);
            String encryptCode2=AESUtil.aesEncrypt(key1,key2);

            System.out.println("encryptCode1="+encryptCode1);
            System.out.println("encryptCode2="+encryptCode2);

            String decryptCode1=AESUtil.aesDecrypt(encryptCode1,key1);

            System.out.println("decryptCode Original="+decryptCode1);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}