package com.city.springboot_big_schoolwork.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("grade_remake")
public class GradeRemake {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Long courseId;
    private Long originalGradeId;
    private String remakeType;
    private Long remakeSemesterId;
    private BigDecimal score;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private String studentName;
    @TableField(exist = false)
    private String courseName;
}
