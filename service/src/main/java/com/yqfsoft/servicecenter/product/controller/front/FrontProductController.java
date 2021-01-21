package com.yqfsoft.servicecenter.product.controller.front;

import com.yqfsoft.servicecenter.product.service.ProductService;
import com.yqfsoft.util.commonresult.CommonResult;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@Api(tags="前端商品")
@RequestMapping("/ProductFront")
public class FrontProductController {

  @Resource
  private ProductService productService;

  @ApiOperation("前端所有商品")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "path", dataType = "Long",defaultValue = "1"),
          @ApiImplicitParam(name = "limit", value = "每页多少条数据", required = true, paramType = "path", dataType = "Long",defaultValue = "30")
  })
  @GetMapping("/menu/{currentPage}/{limit}")
  public CommonResult getMenu(@PathVariable long currentPage, @PathVariable long limit){
    Map<String,Object> map = productService.getMenu(currentPage,limit);
    return CommonResult.ok().data(map);
  }

  @ApiOperation("前端模糊查找商品")
  @GetMapping("/menu/{currentPage}/{limit}/{findInput}")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "currentPage", value = "当前页", required = true, paramType = "path", dataType = "Long",defaultValue = "1"),
          @ApiImplicitParam(name = "limit", value = "每页多少条数据", required = true, paramType = "path", dataType = "Long",defaultValue = "30"),
          @ApiImplicitParam(name = "findInput", value = "模糊查找商品", required = true, paramType = "path", dataType = "String"),
  })
  public CommonResult findfrontProduct(@PathVariable long currentPage,@PathVariable long limit,@PathVariable String findInput) {
    // 这个id和put那个一样是前端看不到的id 通过这个删除 这些要封装impl
    Map<String,Object> map = productService.findfrontProductLikeInput(currentPage,limit,findInput);
    return CommonResult.ok().data(map);
  }
}
