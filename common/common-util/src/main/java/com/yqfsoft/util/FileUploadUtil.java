package com.yqfsoft.util;


import cn.hutool.core.io.FileUtil;
import com.yqfsoft.util.globalexception.MyException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class FileUploadUtil {

  /**
   *
   * @param file 文件
   * @param fileName 给上传成功的文件起一个名字 名字中不包括扩展名
   * @param fisicalPath 文件上传服务器的路径 请用小写扩展名 .jpg .png
   * @param extNameSet 扩展名集合 判断扩展名是否在这个集合中 如果不在 就上传失败
   * @return 图片文件都会变成png
   */
  public static boolean upload(MultipartFile file, String fileName,
                               String fisicalPath, Set<String> extNameSet){
    // 没有文件就直接上传失败
    if (file.isEmpty()){
      throw MyException.file();
    }
    // 获取上传的文件名称
    String fileOriginalName = file.getOriginalFilename();
    // 文件扩展名 jpg (不是。jpg)
    String extName = FileUtil.extName(fileOriginalName);
    // 扩展名去掉点
    extName = extName.toLowerCase();
    if (extNameSet != null || extNameSet.size() > 0){
      // 判断扩展名是否在集合中如果不在上传失败
      boolean inContains = extNameSet.contains(extName);
      if (inContains == false){
        // 文件不合法
        throw MyException.file();
      }
    }
    // 生成新的文件名称
    String newFileName = fileName + "." + extName;
    // 获取文件大小
    // long fileSize = file.getSize();
    // 文件类型
    // String fileType = file.getContentType();
    // 设置上传路径
    // String filePath = fisicalPath;
    // jar包的位置
    // String filePath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    // System.out.println(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
    // 上传后的真实路径
    String path = fisicalPath + newFileName;
    File dest = new File(path);
    // 检测是否存在目录
    if (!dest.getParentFile().exists()) {
      dest.getParentFile().mkdirs();// 新建文件夹
    }
    try {
      // 处理上传
      file.transferTo(dest);// 文件写入
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }
}