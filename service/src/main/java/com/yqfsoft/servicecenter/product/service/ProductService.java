package com.yqfsoft.servicecenter.product.service;

import com.yqfsoft.servicecenter.product.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yqfsoft.servicecenter.product.entity.ProductCategory;
import com.yqfsoft.validation.config.Add;
import com.yqfsoft.validation.config.Update;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Oscar Yang Liu
 * @since 2020-12-04
 */
@CacheConfig(cacheNames = "product")
@Validated
public interface ProductService extends IService<Product> {

  // 查询商品
  @Cacheable(key = "#methodName + ' ' + #currentPage + ' ' + #limit")
  Map<String, Object> getProductList(long currentPage,long limit);
  // 前端查询商品
  @Cacheable(key = "#methodName + ' ' + #currentPage + ' ' + #limit")
  Map<String, Object> getMenu(long currentPage, long limit);

  // 添加单个商品
  @CacheEvict(allEntries=true)
  @Validated(Add.class)
  boolean addProduct(@Valid Product product, MultipartFile file);

  // 模糊查找商品(前端)
  @Cacheable(key = "#root.methodName + ' ' + #findInput")
  Map<String,Object> findproductLikeInput(long currentPage,long limit, String findInput);

  // 模糊查找商品(后端)
  @Cacheable(key = "#root.methodName + ' ' + #findInput")
  Map<String,Object> findfrontProductLikeInput(long currentPage,long limit,String findInput);

  // 通过id来更改商品
  @CacheEvict(allEntries=true)
  @Validated(Update.class)
  boolean updateProductoById(String findId,@Valid Product product, MultipartFile file);

  // 通过商品查找商品
  @Cacheable(key = "#id")
  Product findOne(@NotBlank String id);

  // 删除多个商品
  @CacheEvict(allEntries=true)
  long removeproductByList(String[] ids);

  // Excel导出所有商品
  void getExcelAllProduct(HttpServletResponse response) throws IOException;

  // Excel导入商品
  @CacheEvict(allEntries=true)
  long importProductExcelCount(MultipartFile file, ProductService productService);
}
