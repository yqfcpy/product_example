package com.yqfsoft.util;


import java.util.HashSet;
import java.util.Set;

public class FileNameUtil {

  /**
   * 图片扩展名
   * @return
   */
  public static Set<String> imageExtnameList(){
    Set<String> imageSet = new HashSet<>();
    imageSet.add("jpg");
    imageSet.add("jpeg");
    imageSet.add("png");
    imageSet.add("gif");
    return imageSet;
  }

  /**
   * Excel扩展名
   * @param extname
   * @return
   */
  public static boolean isExcelByExtname(String extname){
    Set<String> excelSet = new HashSet<>();
    excelSet.add("xls");
    excelSet.add("xlsx");
    boolean contains = excelSet.contains(extname);
    return contains;
  }

}
