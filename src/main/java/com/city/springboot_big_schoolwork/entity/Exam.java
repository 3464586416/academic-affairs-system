package com.city.springboot_big_schoolwork.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@TableName("exam")
public class Exam {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String examName;
    private Long courseId;
    private Long semesterId;
    private Long classroomId;
    private LocalDate examDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String examType;
    private String invigilatorIds;
    private Integer capacity;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private String courseName;
    @TableField(exist = false)
    private String roomNo;
    @TableField(exist = false)
    private String semesterName;
    @TableField(exist = false)
    private String invigilatorNames;
}
