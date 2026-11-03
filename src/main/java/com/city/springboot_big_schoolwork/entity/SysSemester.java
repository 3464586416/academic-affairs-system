package com.city.springboot_big_schoolwork.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_semester")
public class SysSemester {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String semesterName;
    private java.time.LocalDate startDate;
    private java.time.LocalDate endDate;
    private Integer isCurrent;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
