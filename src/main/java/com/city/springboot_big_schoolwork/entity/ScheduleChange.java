package com.city.springboot_big_schoolwork.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@TableName("schedule_change")
public class ScheduleChange {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long scheduleId;
    private Long teacherId;
    private String changeType;
    private Long newClassroomId;
    private LocalDate newDate;
    private LocalTime newStartTime;
    private LocalTime newEndTime;
    private String reason;
    private String approveStatus;
    private Long approverId;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private String teacherName;
}
