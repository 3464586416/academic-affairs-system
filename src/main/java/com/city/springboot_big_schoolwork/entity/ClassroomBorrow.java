package com.city.springboot_big_schoolwork.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@TableName("classroom_borrow")
public class ClassroomBorrow {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long classroomId;
    private Long borrowerId;
    private LocalDate borrowDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String purpose;
    private String approveStatus;
    private Long approverId;
    private String approveComment;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private String roomNo;
    @TableField(exist = false)
    private String borrowerName;
}
