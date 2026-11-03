package com.city.springboot_big_schoolwork.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.city.springboot_big_schoolwork.entity.Schedule;
import java.util.List;

public interface ScheduleService extends IService<Schedule> {
    boolean checkConflict(Long teacherId, Long classroomId, Long classId, Long semesterId,
                          Integer dayOfWeek, java.time.LocalTime startTime, java.time.LocalTime endTime,
                          Long excludeId);
}
