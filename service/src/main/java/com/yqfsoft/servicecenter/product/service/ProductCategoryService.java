package com.yqfsoft.servicecenter.product.service;

import com.yqfsoft.servicecenter.product.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yqfsoft.validation.config.Add;
import com.yqfsoft.validation.config.Update;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Oscar Yang Liu
 * @since 2020-12-05
 */
@CacheConfig(cacheNames = "productCategory")
@Validated
public interface ProductCategoryService extends IService<ProductCategory> {

  // 查询所有类别
  @Cacheable(key = "#root.methodName")
  Map<String, Object> getProductCategoryList();

  // 通过id查找商品类别
  @Cacheable(key = "#id")
  ProductCategory getOneProductCategoryById(Integer id);

  // 添加类别
  @CacheEvict(allEntries=true)
  @Validated(Add.class)
  boolean addproductcategory(@Valid ProductCategory productCategory);

  // 修改类别
  @CacheEvict(allEntries=true)
  @Validated(Update.class)
  boolean updateproductcategoryById(Integer id,@Valid ProductCategory productCategory);

  // 批量删除
  @CacheEvict(allEntries=true)
  boolean removeproductcategoryByList(Integer[] ids);

  // Excel导出商品类别
  void getExcelAllProductCategory(HttpServletResponse response) throws IOException;

  // Excel导入商品类别
  @CacheEvict(allEntries=true)
  long importProductCategoryExcelCount(MultipartFile file, ProductCategoryService productCategoryService);
}
