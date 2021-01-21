package com.yqfsoft.servicecenter.product.service.impl;

import cn.hutool.core.io.FileUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yqfsoft.servicecenter.product.entity.Product;
import com.yqfsoft.servicecenter.product.excelListener.ProductExcelListener;
import com.yqfsoft.servicecenter.product.mapper.ProductCategoryMapper;
import com.yqfsoft.servicecenter.product.mapper.ProductMapper;
import com.yqfsoft.servicecenter.product.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yqfsoft.util.ExcelUtil;
import com.yqfsoft.util.FileNameUtil;
import com.yqfsoft.util.FileUploadUtil;
import com.yqfsoft.util.GlobalVariable;
import com.yqfsoft.util.globalexception.MyException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Oscar Yang Liu
 * @since 2020-12-04
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

  @Resource
  private ProductMapper productMapper;
  @Resource
  private ProductCategoryMapper productCategoryMapper;


  /**
   * 后端查询所有商品的分页查询
   * @param currentPage
   * @param limit
   * @return
   */
  @Override
  public Map<String, Object> getProductList(long currentPage,long limit) {
    QueryWrapper<Product> wrapper = new QueryWrapper<>();
    wrapper.orderByAsc("category_id");
    return this.selectProductBackOrFront(currentPage,limit,wrapper);
  }

  /**
   * 前端查询所有商品的分页查询
   * @param currentPage
   * @param limit
   * @return
   */
  @Override
  public Map<String, Object> getMenu(long currentPage, long limit) {
    QueryWrapper<Product> wrapper = new QueryWrapper<>();
    wrapper.orderByAsc("category_id")
            .eq("bloqueado",0);
    return this.selectProductBackOrFront(currentPage,limit,wrapper);
  }

  /** 后端模糊查找
   * 模糊查找商品
   * @param findInput
   * @return
   */
  @Override
  public Map<String,Object> findproductLikeInput(long currentPage,long limit,String findInput) {
    QueryWrapper<Product> wrapper = new QueryWrapper<>();
    wrapper.like("id",findInput)
            .or()
            .like("product_name_es",findInput)
            .or()
            .like("product_name_zh",findInput)
            .or()
            .like("observacion",findInput)
            .orderByAsc("category_id");
    return this.selectProductBackOrFront(currentPage,limit,wrapper);
  }

  /** 前端模糊查找
   * 模糊查找商品
   * @param findInput
   * @return
   */
  @Override
  public Map<String,Object> findfrontProductLikeInput(long currentPage,long limit,String findInput) {
    QueryWrapper<Product> wrapper = new QueryWrapper<>();
    wrapper.eq("bloqueado",0)
            // .and() 里边包上另外条件语句
            .and(wrapper1 -> wrapper1.like("id",findInput)
                    .or()
                    .like("product_name_es",findInput)
                    .or()
                    .like("product_name_zh",findInput)
                    .or()
                    .like("observacion",findInput))
            .orderByAsc("category_id");
    return this.selectProductBackOrFront(currentPage,limit,wrapper);
  }

  /**
   * 提取的商品查询 前端或者后端的提取 包括模糊查找
   * @param currentPage 第多少页
   * @param limit 每页多少条数据
   * @param wrapper 查询条件
   * @return
   */
  public Map<String,Object> selectProductBackOrFront(long currentPage,long limit,QueryWrapper<Product> wrapper){
    Page<Product> pageProduct = new Page<>(currentPage,limit);
    baseMapper.selectPage(pageProduct,wrapper);
    List<Product> list = pageProduct.getRecords();
    long total = pageProduct.getTotal();
    Map<String,Object> map = new HashMap<>();
    map.put("total",total);
    map.put("items",list);
    return map;
  }

  /**
   * 通过id查询商品
   * @param id
   * @return
   */
  @Override
  public Product findOne(String id) {
//    QueryWrapper<Product> wrapper = new QueryWrapper<>();
//    wrapper.eq("id",id);
//    Product product = baseMapper.selectOne(wrapper);
    Product product = baseMapper.selectById(id);
    return product;
  }

  /**
   * 添加单个商品
   * @param product 商品信息
   * @param file 图片文件
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean addProduct(Product product, MultipartFile file) {
    // 编号大写
    product.setId(product.getId().toUpperCase());
    if (file == null){
      boolean productSaveSuccess = save(product);
      return productSaveSuccess;
    }else{
      // 图片扩展名集合
      Set<String> extnameList = FileNameUtil.imageExtnameList();
      // 服务器地址的完整路径
      String fisicalProductImgPath = GlobalVariable.getPATHNGINX() + GlobalVariable.getPATHPRODUCTIMGURL();
      boolean uploadSuccess = FileUploadUtil.upload(file, product.getId(),fisicalProductImgPath, extnameList);
      // 商品url内容处理
      String fileName = file.getOriginalFilename();
      String extName = fileName.substring(fileName.lastIndexOf(".")) ;
      product.setProductImgUrl(GlobalVariable.getPATHPRODUCTIMGURL() + product.getId() + extName);
      boolean productSaveSuccess = save(product);
      if (uploadSuccess == true && productSaveSuccess == true){
        return true;
      }
      return false;
    }
  }


  /**
   * 通过id来修改商品
   * @param findId
   * @param product
   * @param file
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean updateProductoById(String findId, Product product, MultipartFile file) {
    Product dbProduct = baseMapper.selectById(findId);
    if (ObjectUtils.isEmpty(dbProduct)){
      throw MyException.empty();
    }
    String idUpperCase = findId.toUpperCase();
    if (product.getId() != null){
     product.setId(product.getId().toUpperCase());
    }
    String productIdUpperCase = product.getId();
    String dbProductImgurl = dbProduct.getProductImgUrl();
    if (file == null){
      boolean success = this.changeProductIdAndImgurlById(idUpperCase, product,dbProductImgurl);
      return success;
    }else{
      // 删除图片处理 product对象不是空 说明要改id 并且 如果数据库中的数据有一个商品图片 我们就要考虑删除服务器中对应的图片
      // 图片服务器地址的完整路径
      String fisicalProductImgPath = GlobalVariable.getPATHNGINX() + GlobalVariable.getPATHPRODUCTIMGURL();
      if (product.getId() != null && dbProduct.getProductImgUrl() != null){
        String productImgUrl = dbProduct.getProductImgUrl();
        String filname = FileUtil.getName(productImgUrl);
        FileUtil.del(fisicalProductImgPath + filname);
      }
      // 3元运算 id又没有修改 如果修改了 用product对象里的id 如果没有修改id 就用findId
      String chooseIdName = productIdUpperCase == null ? idUpperCase : productIdUpperCase;
      // 图片扩展名集合
      Set<String> extnameList = FileNameUtil.imageExtnameList();
      boolean uploadSuccess = FileUploadUtil.upload(file, chooseIdName, fisicalProductImgPath, extnameList);
      // 商品url内容处理
      String fileName = file.getOriginalFilename();
      String extName = fileName.substring(fileName.lastIndexOf(".")) ;
      product.setProductImgUrl(GlobalVariable.getPATHPRODUCTIMGURL() + chooseIdName + extName);
      boolean productInfoSuccess = productMapper.updateProductInfo(findId, product);
      if (uploadSuccess && productInfoSuccess){
        return true;
      }
      return false;
    }
  }

  /**
   * 通过id修改product信息 没有报过上传文件
   * @param productId 用来标记 id是否被改变
   * @param product 商品修改内容的对象
   * @param dbProductImgUrl 数据库中图片地址值
   * @return
   */
  private boolean changeProductIdAndImgurlById(String productId,Product product,String dbProductImgUrl) {
    if (productId == product.getId()){
      boolean success = productMapper.updateProductInfo(productId,product);
      return success;
    }else{
      if (dbProductImgUrl == null){
        boolean success = productMapper.updateProductInfo(productId,product);
        return success;
      }else{
        // 文件名
        String filename = FileUtil.getName(dbProductImgUrl);
        String extname = FileUtil.extName(filename);
        String newFilename = product.getId().toUpperCase() + "." + extname;
        // 文件的物理路径
        // 图片服务器地址的完整路径
        String fisicalProductImgPath = GlobalVariable.getPATHNGINX() + GlobalVariable.getPATHPRODUCTIMGURL();
        String path = fisicalProductImgPath + filename;
        // 开始重命名文件
        File file = new File(path);
        // 文件是否存在
        if (!file.exists()){
          // 如果文件是空的话 这里写一个默认文件
          return true;
        }
        try {
          // 开始改名
          FileUtil.rename(file,newFilename,true);
        }catch (Exception e){
          e.printStackTrace();
        }
        product.setProductImgUrl(GlobalVariable.getPATHPRODUCTIMGURL() + newFilename);
        boolean success = productMapper.updateProductInfo(productId,product);
        return success;
      }
    }
  }

  /**
   * 通过id批量删除商品
   * @param ids
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public long removeproductByList(String[] ids) {
    long deletCount = 0;
    for (String id:ids){
      String imgUrl = productMapper.selectImgPathByid(id);
      if (imgUrl != null){
        FileUtil.del(GlobalVariable.getPATHNGINX() + imgUrl);
      }
      try{
        int i = baseMapper.deleteById(id);
        if (i > 0){
          deletCount++;
        }
      }catch(Exception e){
        log.error("删除出现异常 " + id);
        e.printStackTrace();
        continue;
      }
    }
    return deletCount;
  }

  /**
   * 导出所有商品的Excel
   * @param response
   */
  @Override
  public void getExcelAllProduct(HttpServletResponse response) throws IOException {
    //
    ExcelUtil.exportExcel("Product",response);
    // 把数据放到里边
    EasyExcel.write(response.getOutputStream(), Product.class).sheet("Sheet1").doWrite(this.allProduct());
  }

  /**
   * Excel文件导入数据库
   * @param file
   * @param productService
   * @return
   */
  //@Transactional(rollbackFor = Exception.class)
  @Override
  public long importProductExcelCount(MultipartFile file, ProductService productService) {
    String filename = file.getOriginalFilename();
    String extname = FileUtil.extName(filename).toLowerCase();
    if (!FileNameUtil.isExcelByExtname(extname)){
      log.error("导入的Excel格式不正确");
      throw MyException.excel();
    }
    // 商品分类列表中的id set集合 用来判断商品表中商品类别category_id是否在商品类别表ID中
    Set<Integer> productCategoryBatchIds = productCategoryMapper.getProductCategoryBatchIds();
    // log.info("商品分类id集合" + productCategoryIdSet);
    // 在这里实例化 可以找到数量
    ProductExcelListener productExcelListener = new ProductExcelListener(productService, productCategoryBatchIds);
    try{
      // 文件输入流
      InputStream input = file.getInputStream();

      // 调用方法进行读取
      EasyExcel.read(input,Product.class,productExcelListener).sheet().doRead();
    }catch(Exception e){
      e.printStackTrace();
      log.error("文件流传输失败");
      return 0;
    }
    return productExcelListener.getWriteSuccessCount();
  }

  /**
   * 配合Excel导出所有商品 查询所有商品
   * @return
   */
  private List<Product> allProduct(){
    QueryWrapper<Product> wrapper = new QueryWrapper<>();
    wrapper.orderByAsc("category_id");
    List<Product> products = baseMapper.selectList(wrapper);
    return products;
  }
}
