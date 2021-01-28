package com.yqfsoft.servicecenter.product.excelListener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yqfsoft.servicecenter.product.entity.Product;
import com.yqfsoft.servicecenter.product.service.ProductService;
import com.yqfsoft.util.GlobalVariable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Slf4j
public class ProductExcelListener extends AnalysisEventListener<Product> {

  public ProductService productService;
  public Set<Integer> productCategoryBatchIds;
  // 每次读取多少条数据进行保存操作
  private static final int BATCH_COUNT = GlobalVariable.getEXCELIMPORTSIZE();
  // 由于每次读都是新new UserInfoDataListener的，所以这个list不会存在线程安全问题
  private List<Product> list;
  // 多少条导入成功
  @Getter
  private long writeSuccessCount;

  // public ProductExcelListener(){};
  public ProductExcelListener(ProductService productService,Set<Integer> productCategoryBatchIds){
    this.list = new ArrayList<>();
    this.writeSuccessCount = 0;
    this.productService = productService;
    this.productCategoryBatchIds = productCategoryBatchIds;
  }

  @Override
  public void invoke(Product product, AnalysisContext analysisContext) {
    log.info("读取一行信息" + product);
    product.setId(product.getId().toUpperCase());
    list.add(product);
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
      boolean contains = productCategoryBatchIds.contains(list.get(i).getCategoryId());
      if (!contains){
        log.error("导入商品中的类别id，没有在商品类别表中找到");
        continue;
      }
      try{
        boolean isSuccess = productService.addProduct(list.get(i), null);
        if (isSuccess){
          writeSuccessCount++;
          log.info("写入数据成功：" + list.get(i));
        }
      }catch (Exception e) {
        log.error("写入数据异常 检查Product类数据校验 " + list.get(i));
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
