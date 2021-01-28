package com.yqfsoft.servicecenter.product.excelListener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yqfsoft.servicecenter.product.entity.ProductCategory;
import com.yqfsoft.servicecenter.product.service.ProductCategoryService;
import com.yqfsoft.util.GlobalVariable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class ProductCategoryExcelListener extends AnalysisEventListener<ProductCategory> {
  public ProductCategoryService productCategoryService;
  // 每次读取多少条数据进行保存操作 做了一个统一管理
  private static final int BATCH_COUNT = GlobalVariable.getEXCELIMPORTSIZE();
  // 由于每次读都是新new UserInfoDataListener的，所以这个list不会存在线程安全问题
  private List<ProductCategory> list;
  // 多少条导入成功
  @Getter
  private long writeSuccessCount;

  // public ProductCategoryExcelListener(){};
  public ProductCategoryExcelListener(ProductCategoryService productCategoryService){
    this.list = new ArrayList<>();
    this.writeSuccessCount = 0;
    this.productCategoryService = productCategoryService;
  }

  @Override
  public void invoke(ProductCategory productCategory, AnalysisContext analysisContext) {
    log.info("读取一行信息" + productCategory);
    list.add(productCategory);
    if (list.size() >= BATCH_COUNT) {
      // 保存数据
      log.info("开始保存数据......" + list);
      saveData();
      // 存储完成清理 list
      list.clear();
    }
  }
  // 保存数据的方法
  private void saveData() {
    for (int i = 0;i<list.size();i++){

      try{
        boolean isSuccess = productCategoryService.addproductcategory(list.get(i));
        if (isSuccess){
          writeSuccessCount++;
          log.info("写入数据成功：" + list.get(i));
        }
      }catch (Exception e) {
        log.error("写入数据异常 检查ProductCategory类数据校验 " + list.get(i));
        continue;
      }
    }
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    // 监听器结束之后运行的东西
    // 把最后集合里数据保存
    saveData();
  }
}
