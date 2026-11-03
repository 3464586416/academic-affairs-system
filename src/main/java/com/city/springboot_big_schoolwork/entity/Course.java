package com.city.springboot_big_schoolwork.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("course")
public class Course {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String courseNo;
    private String courseName;
    private String courseType;
    private BigDecimal credits;
    private Integer classHours;
    private Integer theoryHours;
    private Integer practiceHours;
    private Long deptId;
    private Long teacherId;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private String deptName;
    @TableField(exist = false)
    private String teacherName;
    @TableField(exist = false)
    private String prerequisiteNames;
}
