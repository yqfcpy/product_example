package com.yqfsoft.servicecenter.product.mapper;

import com.yqfsoft.servicecenter.product.entity.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Oscar Yang Liu
 * @since 2020-12-05
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {

  // 更改商品类别信息
  boolean updateProductCategoryInfo(Integer findId,ProductCategory productCategory);

  // 获取所有商品id
  @Select("SELECT id FROM product_category;")
  Set<Integer> getProductCategoryBatchIds();
}
