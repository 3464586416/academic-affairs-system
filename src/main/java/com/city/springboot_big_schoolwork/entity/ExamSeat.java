package com.city.springboot_big_schoolwork.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("exam_seat")
public class ExamSeat {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long examId;
    private Long studentId;
    private Integer seatNo;
    private String attendStatus;

    @TableField(exist = false)
    private String studentName;
    @TableField(exist = false)
    private String studentNo;
}
