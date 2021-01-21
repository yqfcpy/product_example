package com.yqfsoft.servicecenter.product.controller;


import com.yqfsoft.servicecenter.product.entity.Product;
import com.yqfsoft.servicecenter.product.service.ProductService;
import com.yqfsoft.util.commonresult.CommonResult;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
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
 * @since 2020-12-04
 */
@Api(tags = "商品")
@Validated
@RestController
@RequestMapping("/product")
public class ProductController {

  @Resource
  private ProductService productService;

  @ApiOperation("查询所有商品")
  @GetMapping("/{currentPage}/{limit}")
  // 解决Swagger2分页的坑 long类型最好这样写
  @ApiImplicitParams({
          @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "path", dataType = "Long",defaultValue = "1"),
          @ApiImplicitParam(name = "limit", value = "每页多少条数据", required = true, paramType = "path", dataType = "Long",defaultValue = "30")
  })
  public CommonResult findAll(@PathVariable long currentPage,@PathVariable long limit){

    // 这个方法就能得到数据
    Map<String,Object> map = productService.getProductList(currentPage,limit);
    return CommonResult.ok().data(map);
  }

  // 添加商品图片

/*
*  通过id来更改 这里的 Product product 是修改后的内容
* */
  @ApiOperation("根据id更改商品")
  //@ApiImplicitParam(name = "file", value = "添加单个商品图片", required = false,paramType = "form", dataType = "__file")
  @PutMapping("/update/{findId}")
  public CommonResult update(@PathVariable String findId,Product product,
                             @RequestParam(required = false) MultipartFile file){
    boolean isSuccess = productService.updateProductoById(findId,product,file);
    if(isSuccess){
      return CommonResult.ok();
    }else{
      return CommonResult.fail();
    }
  }

  @ApiOperation("添加单个商品图片")
  @ApiImplicitParam(name = "file", value = "添加单个商品图片", required = false,paramType = "form", dataType = "__file")
  @PostMapping("/addproduct")
  public CommonResult addproductimg(Product product, @RequestParam(required = false) MultipartFile file){
    boolean isSuccess = productService.addProduct(product,file);
    if(isSuccess){
      return CommonResult.ok();
    }else{
      return CommonResult.fail();
    }
  }

  @ApiOperation("模糊查找商品")
  @GetMapping("/findproduct/{currentPage}/{limit}/{findInput}")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "path", dataType = "Long",defaultValue = "1"),
          @ApiImplicitParam(name = "limit", value = "每页多少条数据", required = true, paramType = "path", dataType = "Long",defaultValue = "30"),
          @ApiImplicitParam(name = "findInput", value = "模糊查找商品", required = true, paramType = "path", dataType = "String"),
  })
  public CommonResult findproduct(@PathVariable long currentPage,@PathVariable long limit,@PathVariable String findInput) {
    // 这个id和put那个一样是前端看不到的id 通过这个删除 这些要封装impl
    Map<String,Object> map = productService.findproductLikeInput(currentPage,limit,findInput);
    return CommonResult.ok().data(map);
  }

  /**
   * 通过商品查找商品
   */
  @ApiOperation("通过商品id查找商品")
  @ApiImplicitParam(name = "id", value = "通过商品id查找商品", required = true,paramType = "path", dataType = "String")
  @GetMapping("/findone/{id}")
  public CommonResult findOne(@PathVariable String id) {
    // 这个id和put那个一样是前端看不到的id 通过这个删除 这些要封装impl
    Product resultfind = productService.findOne(id);
    Map<String,Object> map = new HashMap();
    map.put("items",resultfind);
    return CommonResult.ok().data(map);
  }

  @ApiOperation("删除多个商品")
  @DeleteMapping("/remove/")
  public CommonResult removeOne(@ApiParam(name = "id",value = "商品id", required = true)
                                @RequestBody String[] ids) {
    // 这个id和put那个一样是前端看不到的id 通过这个删除 这些要封装impl
    long successCount = productService.removeproductByList(ids);
    if(successCount > 0){
      return CommonResult.ok().data("Total",successCount);
    }else{
      return CommonResult.fail();
    }
  }

  @ApiOperation("Excel导出商品")
  @GetMapping("/exportProductExcel")
  public void exportProductExcel(HttpServletResponse response) throws IOException {
    productService.getExcelAllProduct(response);
  }

  @ApiOperation("Excel导入商品")
  @PostMapping("/importProductExcel")
  public CommonResult importProductExcel(MultipartFile file){
    // 这个方法要在服务层中写 要把商品分类放进去判断 传入的商品分类在商品分类列中存在
    long successCount = productService.importProductExcelCount(file, productService);
    if(successCount>0){
      return CommonResult.ok().data("Total",successCount);
    }else{
      return CommonResult.fail();
    }
  }
}