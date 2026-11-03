package com.city.springboot_big_schoolwork.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@TableName("schedule")
public class Schedule {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long courseId;
    private Long teacherId;
    private Long classroomId;
    private Long classId;
    private Long semesterId;
    private Integer dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer weekStart;
    private Integer weekEnd;
    private Integer sectionStart;
    private Integer sectionEnd;
    private String scheduleType;
    private Long originalScheduleId;
    private String remark;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private String courseName;
    @TableField(exist = false)
    private String teacherName;
    @TableField(exist = false)
    private String roomNo;
    @TableField(exist = false)
    private String className;
    @TableField(exist = false)
    private String semesterName;
}
