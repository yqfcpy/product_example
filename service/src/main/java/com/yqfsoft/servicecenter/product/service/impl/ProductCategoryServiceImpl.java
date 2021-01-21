package com.yqfsoft.servicecenter.product.service.impl;

import cn.hutool.core.io.FileUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yqfsoft.servicecenter.product.entity.ProductCategory;
import com.yqfsoft.servicecenter.product.excelListener.ProductCategoryExcelListener;
import com.yqfsoft.servicecenter.product.mapper.ProductCategoryMapper;
import com.yqfsoft.servicecenter.product.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yqfsoft.util.ExcelUtil;
import com.yqfsoft.util.FileNameUtil;
import com.yqfsoft.util.globalexception.MyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Oscar Yang Liu
 * @since 2020-12-05
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

  @Resource
  private ProductCategoryMapper productCategoryMapper;

  /**
   * 查询所有类别
   * @return
   */
  @Override
  public Map<String, Object> getProductCategoryList() {

    QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
    wrapper.orderByAsc("id");
    List<ProductCategory> pcList = baseMapper.selectList(wrapper);
    long total = pcList.size();

    Map<String,Object> map = new HashMap<>();
    map.put("total",total);
    map.put("items",pcList);
    return map;
  }

  /**
   * 通过id查找商品类别
   * @param id
   * @return
   */
  @Override
  public ProductCategory getOneProductCategoryById(Integer id) {
    ProductCategory productCategory = baseMapper.selectById(id);
    return productCategory;
  }

  /**
   * 添加类别
   * @param productCategory
   * @return
   */
  @Override
  public boolean addproductcategory(ProductCategory productCategory) {
    boolean isSuccess = save(productCategory);
    return isSuccess;
  }

  /**
   * 修改商品类别
   * @param id 查找的Id
   * @param productCategory 修改商品类别信息
   * @return
   */
  @Override
  public boolean updateproductcategoryById(Integer id, ProductCategory productCategory) {
    boolean isSuccess = productCategoryMapper.updateProductCategoryInfo(id, productCategory);
    return isSuccess;
  }

  /**
   * 通过一个数组删除商品分类
   * @param ids
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean removeproductcategoryByList(Integer[] ids) {
    for (Integer id:ids){
      int i = baseMapper.deleteById(id);
      if (i == 0){
        return false;
      }
    }
    return true;
  }

  /**
   * 查询所有商品类别
   * @param response
   * @throws IOException
   */
  @Override
  public void getExcelAllProductCategory(HttpServletResponse response) throws IOException {
    ExcelUtil.exportExcel("ProductCategory",response);
    // 把数据放到里边
    EasyExcel.write(response.getOutputStream(), ProductCategory.class).sheet("Sheet1").doWrite(this.allProductCategory());
  }

  /**
   * 关联Excel导出
   * @return
   */
  private List allProductCategory() {
    QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
    wrapper.orderByAsc("id");
    List<ProductCategory> ProductCategoryList = baseMapper.selectList(wrapper);
    return ProductCategoryList;
  }

  @Override
  public long importProductCategoryExcelCount(MultipartFile file, ProductCategoryService productCategoryService) {
    String filename = file.getOriginalFilename();
    String extname = FileUtil.extName(filename).toLowerCase();
    if (!FileNameUtil.isExcelByExtname(extname)){
      log.error("导入的Excel格式不正确");
      throw MyException.excel();
    }
    // 在这里实例化 可以找到数量
    ProductCategoryExcelListener productCategoryExcelListener = new ProductCategoryExcelListener(productCategoryService);
    try{
      // 文件输入流
      InputStream input = file.getInputStream();

      // 调用方法进行读取
      EasyExcel.read(input, ProductCategory.class,productCategoryExcelListener).sheet().doRead();
    }catch(Exception e){
      e.printStackTrace();
      log.error("文件流传输失败");
      return 0;
    }
    return productCategoryExcelListener.getWriteSuccessCount();
  }
}
