package com.city.springboot_big_schoolwork.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("student_selection")
public class StudentSelection {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Long courseId;
    private Long semesterId;
    private Long phaseId;
    private String selectionStatus;
    private Integer isLottery;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime selectedAt;

    @TableField(exist = false)
    private String studentName;
    @TableField(exist = false)
    private String studentNo;
    @TableField(exist = false)
    private String courseName;
    @TableField(exist = false)
    private String courseNo;
}
