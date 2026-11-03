package com.city.springboot_big_schoolwork.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("course_textbook")
public class CourseTextbook {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long courseId;
    private Long textbookId;
    private Long semesterId;
    private Integer isRequired;
    private Integer quantity;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(exist = false)
    private String courseName;
    @TableField(exist = false)
    private String textbookTitle;
}
