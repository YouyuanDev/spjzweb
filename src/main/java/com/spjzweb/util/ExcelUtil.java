package com.spjzweb.util;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    //合同数据导入专用

    public static int CONTRACT_NO_INDEX_i=3;
    public static int CONTRACT_NO_INDEX_j=1;

    public static int CUSTOMER_SPEC_INDEX_i=4;
    public static int CUSTOMER_SPEC_INDEX_j=11;

    public static int  MACHINING_CONTRACT_NO_INDEX_i=3;
    public static int  MACHINING_CONTRACT_NO_INDEX_j=13;


    public static int OD_WT_INDEX_i=3;
    public static int OD_WT_INDEX_j=9;

    public static int LOT_NO_INDEX_i=3;
    public static int LOT_NO_INDEX_j=5;

    public static int MATERIAL_NO_INDEX_i=3;
    public static int MATERIAL_NO_INDEX_j=15;

    public static int STEEL_GRADE_INDEX_i=5;
    public static int STEEL_GRADE_INDEX_j=1;


    public static int THREADING_TYPE_INDEX_i=6;
    public static int THREADING_TYPE_INDEX_j=3;


    public  static int COUPING_TYPE_INDEX_i=6;
    public  static int COUPING_TYPE_INDEX_j=5;

    public static boolean isNumeric00(String str)
    {
        try{
            Float.parseFloat(str);
            return true;
        }catch(NumberFormatException e)
        {
            System.out.println("异常：\"" + str + "\"不是数字..");
            return false;
        }
    }

    //readFromFiletoList
    public static List<List<Object>> readFromFiletoList(String fileFullname){

        //fileFullname="/Users/kurt/Documents/apache-tomcat-8.5.27/webapps/ROOT/upload/pipes/pipes.xls";
        List<List<Object>> datalist = new ArrayList<List<Object>>();

        File file = new File(fileFullname);
        StringBuffer sb = new StringBuffer();
        try {

            Workbook book = Workbook.getWorkbook(file);
            try{
                //支持多sheet上传
                for(int sh=0;sh<book.getNumberOfSheets();sh++) {
                    Sheet sheet = book.getSheet(sh);

                    for (int i = 0; i < sheet.getRows(); i++) {
                        List<Object> rowitem = new ArrayList<Object>();
                        for (int j = 0; j < sheet.getColumns(); j++) {
                            //第一个参数代表列，第二个参数代表行。(默认起始值都为0)
                            sb.append(sheet.getCell(j, i).getContents() + "\t");
                            rowitem.add(sheet.getCell(j, i).getContents());

                        }
                        sb.append("\n");

                        datalist.add(rowitem);
                    }
                }
                System.out.println(sb);
            }finally{
                if(book != null){
                    book.close();
                    System.err.println("datalist size="+datalist.size());
                    return datalist;
                }
            }

        } catch (BiffException e) {
            System.err.println(e+"");

        } catch (IOException e) {
            System.err.println(e+"文件读取错误");
        }

        return null;
    }//end readFromFile

}
