package com.yqfsoft.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Objects;


public class FilterUtil {
  /**
   * 去掉字符串所有的空格
   * @param str
   * @return
   */
  public static String removeAllBlankFilter(String str){
      str = str.replaceAll(" ","");
      return str;
  }

  /**
   * 去掉字符串开头结尾的空格 和 字符串中间的过多空格 并且首字母大写
   * @param str
   * @return
   */
  public static String removeRedundantBlankFilter(String str){
    StringTokenizer pas = new StringTokenizer(str," ");
    // 这里清空了str，但StringTokenizer对象中已经保留了原来字符串的内容。
    str="";
    while (pas.hasMoreTokens()){
      String s = pas.nextToken();
      str = str + s + " ";
    }
    str = str.trim();
    str = str.toUpperCase();
    return str;
  }

  /**
   * 校验email
   * @param email
   * @return
   */
  public static Boolean isEmail(String email){
    // 如果是空的就不用判断了
    if (null==email || "".equals(email)) return false;
    // Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
    Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
    Matcher m = p.matcher(email);
    return m.matches();
  }

  /**
   * 密码 需要最少8位 使用最少要使用3种方式
   * @param password
   * @return
   */
  public static  Boolean checkPassword(String password){
    // 数字
    final String REG_NUMBER = ".*\\d+.*";
    // 小写字母
    final String REG_UPPERCASE = ".*[A-Z]+.*";
    // 大写字母
    final String REG_LOWERCASE = ".*[a-z]+.*";
    // 特殊符号
    final String REG_SYMBOL = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";
    // 密码长度
    final Integer passwordLength = 8;
    // 以上四种方式 最少用几种方式
    final Integer passwordWayAtLeastCount = 3;

    //密码为空或者长度小于8位则返回false
    if (password == null || password.length() < passwordLength ) return false;
    int i = 0;
    if (password.matches(REG_NUMBER)) i++;
    if (password.matches(REG_LOWERCASE))i++;
    if (password.matches(REG_UPPERCASE)) i++;
    if (password.matches(REG_SYMBOL)) i++;

    if (i  < passwordWayAtLeastCount )  return false;

    return true;
  }

  /**
   * 字符串 去掉所有空格 如果字母全大写
   * @param stringUpperCaseFilter
   * @return
   */
  public static String stringUpperCaseFilter(String stringUpperCaseFilter){
    stringUpperCaseFilter = FilterUtil.removeAllBlankFilter(stringUpperCaseFilter);
    stringUpperCaseFilter = stringUpperCaseFilter.toUpperCase();
    return stringUpperCaseFilter;
  }

  /**
   * 把一句话中每个字符串的首字母都大写
   * @param stringFirstUpperCaseEveryoneFilter
   * @return
   */
  public static String StringFirstUpperCaseEveryoneFilter(String stringFirstUpperCaseEveryoneFilter){
    stringFirstUpperCaseEveryoneFilter = FilterUtil.removeRedundantBlankFilter(stringFirstUpperCaseEveryoneFilter);
    stringFirstUpperCaseEveryoneFilter = stringFirstUpperCaseEveryoneFilter.toLowerCase();
    String[] strs = stringFirstUpperCaseEveryoneFilter.split(" ");
    StringBuilder sb = new StringBuilder();
    for (String strTmp : strs) {
      char[] ch = strTmp.toCharArray();
      if (ch[0] >= 'a' && ch[0] <= 'z') {
        ch[0] = (char) (ch[0] - 32);
      }
      String strT = new String(ch);
      sb.append(strT).append(" ");
    }
    return sb.toString().trim();
  }

  /**
   * 判断一个对象里边的数想是不是都为空
   * @param obj
   * @return
   */
  public static boolean allFieldIsNull(Object obj) {
    Boolean flag = true;
    try{
      // 得到类对象
      Class stuCla = obj.getClass();
      //得到属性集合
      Field[] fs = stuCla.getDeclaredFields();
      //遍历属性
      for (Field f : fs) {
        // 设置属性是可以访问的(私有的也可以)
        f.setAccessible(true);
        // 得到此属性的值
        Object val = f.get(obj);
        //只要有1个属性不为空,那么就不是所有的属性值都为空
        if(val != null) {
          flag = false;
          break;
        }
      }
    }catch (Exception e){
      e.printStackTrace();
    }
    return flag;

  }

  /**
   * 判断是否是数字
   * @param str
   * @return
   */
  public static boolean isNumber (String str) {
    // null or empty
    if (str == null || str.length() == 0) {
      return false;
    }
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      try {
        Double.parseDouble(str);
        return true;
      } catch (NumberFormatException ex) {
        try {
          Float.parseFloat(str);
          return true;
        } catch (NumberFormatException exx) {
          return false;
        }
      }
    }
  }

  /**
   * 时间存数字 年月日时分秒毫秒
   * @return
   */
  public static String datetimeWithNumber(){
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");
    String format = sdf.format(date);
    return format;
  }
}
