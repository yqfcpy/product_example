package com.yqfsoft.servicecenter.product.entity;

import java.math.BigDecimal;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yqfsoft.validation.config.Add;
import com.yqfsoft.validation.config.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author Oscar Yang Liu
 * @since 2020-12-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Product对象", description="")
@ContentRowHeight(18)
@ExcelIgnoreUnannotated // 没有标注@ExcelProperty注解 不会参与读写
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @SuppressWarnings("deprecation") // 抑制过期方法警告
    @ApiModelProperty("商品id")
    @ExcelProperty
    @ColumnWidth(6)
    @TableId(type = IdType.ID_WORKER_STR)
    @NotBlank(groups = {Add.class})
    private String id;

    @ApiModelProperty("西班牙文名称")
    @ExcelProperty
    @ColumnWidth(50)
    private String productNameEs;

    @ApiModelProperty("中文名称")
    @ExcelProperty
    @ColumnWidth(25)
    private String productNameZh;

    @ApiModelProperty("类别")
    @ExcelProperty
    @ColumnWidth(15)
    @NotNull(groups = {Add.class})
    private Integer categoryId;

    @ApiModelProperty("商品是否可以销售")
    @TableField(fill= FieldFill.INSERT)
    private Boolean bloqueado;

    @ApiModelProperty("商品价格")
    @ExcelProperty
    @ColumnWidth(10)
    @DecimalMin(groups = {Add.class, Update.class},value = "0.01")
    private BigDecimal precio;

    @ApiModelProperty("图片地址")
    @Null(groups = {Add.class,Update.class})
    private String productImgUrl;

    @ApiModelProperty("商品详情")
    private String observacion;

}
