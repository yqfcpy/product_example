package com.yqfsoft.servicecenter.product.mapper;

import com.yqfsoft.servicecenter.product.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Oscar Yang Liu
 * @since 2020-12-04
 */
public interface ProductMapper extends BaseMapper<Product> {
  // 更改商品信息
  boolean updateProductInfo(String findId,Product product);

  // 通过商品id查找图片地址
  @Select("SELECT product_img_url FROM product WHERE id = #{id}")
  String selectImgPathByid(String id);
}
