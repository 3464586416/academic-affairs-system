package com.city.springboot_big_schoolwork.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("textbook_distribute")
public class TextbookDistribute {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long textbookId;
    private Long studentId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private LocalDate distributeDate;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(exist = false)
    private String textbookTitle;
    @TableField(exist = false)
    private String studentName;
}
