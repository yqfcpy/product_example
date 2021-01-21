package com.yqfsoft.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class ExcelUtil {

  /**
   * 导出Excel
   * @param name 文件名字
   * @param response
   * @throws IOException
   */
  public static void exportExcel(String name,HttpServletResponse response) throws IOException {
    String dateName = FilterUtil.datetimeWithNumber();
    String filename = URLEncoder.encode(dateName + name, "utf-8");
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx");
  }
}
