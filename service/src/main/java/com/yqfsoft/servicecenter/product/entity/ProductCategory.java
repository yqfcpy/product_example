package com.yqfsoft.servicecenter.product.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.yqfsoft.validation.config.Add;
import com.yqfsoft.validation.config.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * <p>
 * 
 * </p>
 *
 * @author Oscar Yang Liu
 * @since 2020-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProductCategory对象", description="")
@ContentRowHeight(18)
@ExcelIgnoreUnannotated
public class ProductCategory implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty("类别编号")
    @SuppressWarnings("deprecation")
    @ExcelProperty
    @ColumnWidth(6)
    @TableId(value = "id" ,type = IdType.ID_WORKER)
    private Integer id;

    @ExcelProperty
    @ColumnWidth(30)
    @ApiModelProperty("类别西班牙名称")
    @NotBlank(groups = {Add.class})
    private String productCategoryNameEs;

    @ExcelProperty
    @ColumnWidth(30)
    @ApiModelProperty("类别中文名称")
    private String productCategoryNameZh;
}
