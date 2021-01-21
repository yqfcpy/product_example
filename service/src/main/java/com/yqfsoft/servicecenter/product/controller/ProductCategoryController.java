package com.yqfsoft.servicecenter.product.controller;


import com.yqfsoft.servicecenter.product.entity.ProductCategory;
import com.yqfsoft.servicecenter.product.service.ProductCategoryService;
import com.yqfsoft.util.commonresult.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Oscar Yang Liu
 * @since 2020-12-05
 */
@Api(tags = "商品分类")
@RestController
@RequestMapping("/product-category")
public class ProductCategoryController {

  @Resource
  private ProductCategoryService productCategoryService;

  @ApiOperation("商品分类表")
  @GetMapping("/findAll")
  public CommonResult findAll(){
    Map<String,Object> map = productCategoryService.getProductCategoryList();
    return CommonResult.ok().data(map);
  }

  @ApiOperation("商品类别id查询")
  @ApiImplicitParam(name = "id", value = "商品类别id", required = true,paramType = "path", dataType = "int")
  @GetMapping("/findOne/{id}")
  public CommonResult findOne(@PathVariable Integer id){
    ProductCategory productCategory = productCategoryService.getOneProductCategoryById(id);
    Map<String,ProductCategory> map = new HashMap<>();
    return CommonResult.ok().data("items",productCategory);
  }

  @ApiOperation("添加单个商品分类")
  @PostMapping("/addproductcategory")
  public CommonResult createOne(@ApiParam(name = "商品分类对象",value = "商品分类对象", required = true)
                                @RequestBody ProductCategory productCategory){
    boolean isSuccess = productCategoryService.addproductcategory(productCategory);
    if(isSuccess){
      return CommonResult.ok();
    }else{
      return CommonResult.fail();
    }
  }

  @ApiOperation("根据category_id删除商品分类 ")
  @PutMapping("/update/{id}")
  public CommonResult update(@ApiParam(name = "id",value = "id", required = true)
                             @PathVariable Integer id,
                             @ApiParam(name = "传入商品分类新对象",value = "传入商品分类新对象", required = true)
                             @RequestBody ProductCategory productCategory){
    boolean isSuccess = productCategoryService.updateproductcategoryById(id, productCategory);
    if(isSuccess){
      return CommonResult.ok();
    }else{
      return CommonResult.fail();
    }
  }

  @ApiOperation("删除多个商品分类")
  @DeleteMapping("/remove/")
  public CommonResult removeOne(@ApiParam(name = "id",value = "商品分类id", required = true)
                                @RequestBody Integer[] ids) {
    // 这个id和put那个一样是前端看不到的id 通过这个删除 这些要封装impl
    boolean isSuccess = productCategoryService.removeproductcategoryByList(ids);
    if(isSuccess){
      return CommonResult.ok();
    }else{
      return CommonResult.fail();
    }
  }

  @ApiOperation("Excel导出商品类别")
  @GetMapping("/exportProductCategoryExcel")
  public void exportProductCategoryExcel(HttpServletResponse response) throws IOException {
    productCategoryService.getExcelAllProductCategory(response);
  }

  @ApiOperation("Excel导入商品类别")
  @PostMapping("/importProductCategoryExcel")
  public CommonResult importProductCategoryExcel(MultipartFile file){
    // 这个方法要在服务层中写 要把商品分类放进去判断 传入的商品分类在商品分类列中存在
    long successCount = productCategoryService.importProductCategoryExcelCount(file, productCategoryService);
    if(successCount>0){
      return CommonResult.ok().data("Total",successCount);
    }else{
      return CommonResult.fail();
    }
  }

}

