package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.Schedule;
import com.city.springboot_big_schoolwork.mapper.ScheduleMapper;
import com.city.springboot_big_schoolwork.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {

    @Override
    public boolean checkConflict(Long teacherId, Long classroomId, Long classId, Long semesterId,
                                  Integer dayOfWeek, LocalTime startTime, LocalTime endTime,
                                  Long excludeId) {
        LambdaQueryWrapper<Schedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Schedule::getSemesterId, semesterId)
                .eq(Schedule::getDayOfWeek, dayOfWeek)
                .eq(Schedule::getStatus, 1)
                .ne(excludeId != null, Schedule::getId, excludeId);
        List<Schedule> schedules = list(wrapper);

        for (Schedule s : schedules) {
            boolean timeOverlap = startTime.isBefore(s.getEndTime()) && endTime.isAfter(s.getStartTime());
            if (!timeOverlap) continue;

            boolean teacherConflict = s.getTeacherId().equals(teacherId);
            boolean classroomConflict = s.getClassroomId().equals(classroomId);
            boolean classConflict = s.getClassId() != null && s.getClassId().equals(classId);

            if (teacherConflict || classroomConflict || classConflict) {
                return true;
            }
        }
        return false;
    }
}
