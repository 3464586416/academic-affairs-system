package com.city.springboot_big_schoolwork.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("course_prerequisite")
public class CoursePrerequisite {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long courseId;
    private Long prerequisiteId;
}
