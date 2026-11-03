package com.city.springboot_big_schoolwork.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.springboot_big_schoolwork.common.PageResult;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.entity.Schedule;
import com.city.springboot_big_schoolwork.entity.ScheduleChange;
import com.city.springboot_big_schoolwork.service.ScheduleChangeService;
import com.city.springboot_big_schoolwork.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/scheduling")
public class SchedulingController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleChangeService scheduleChangeService;

    @GetMapping
    public String index() {
        return "scheduling/index";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<Schedule>> list(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) Long semesterId,
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) Long classId) {
        LambdaQueryWrapper<Schedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(semesterId != null, Schedule::getSemesterId, semesterId)
                .eq(teacherId != null, Schedule::getTeacherId, teacherId)
                .eq(classId != null, Schedule::getClassId, classId)
                .eq(Schedule::getStatus, 1)
                .orderByAsc(Schedule::getDayOfWeek)
                .orderByAsc(Schedule::getSectionStart);
        Page<Schedule> p = scheduleService.page(new Page<>(page, size), wrapper);
        return Result.success(PageResult.of(p));
    }

    @PostMapping("/save")
    @ResponseBody
    @OperationLog("保存排课信息")
    public Result<?> save(@RequestBody Schedule schedule) {
        // 冲突检测
        boolean conflict = scheduleService.checkConflict(
                schedule.getTeacherId(),
                schedule.getClassroomId(),
                schedule.getClassId(),
                schedule.getSemesterId(),
                schedule.getDayOfWeek(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getId()
        );
        if (conflict) {
            return Result.error("排课冲突：教师、教室或班级时间冲突");
        }
        schedule.setStatus(1);
        schedule.setScheduleType("正常");
        scheduleService.saveOrUpdate(schedule);
        return Result.success("排课成功");
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @OperationLog("删除排课信息")
    public Result<?> delete(@PathVariable Long id) {
        Schedule schedule = scheduleService.getById(id);
        if (schedule != null) {
            schedule.setStatus(0);
            scheduleService.updateById(schedule);
        }
        return Result.success();
    }

    // 调课申请
    @GetMapping("/change")
    public String changeIndex() {
        return "scheduling/index";
    }

    @PostMapping("/change/save")
    @ResponseBody
    @OperationLog("提交调课申请")
    public Result<?> changeSave(@RequestBody ScheduleChange change) {
        change.setApproveStatus("待审批");
        change.setStatus(1);
        scheduleChangeService.save(change);
        return Result.success("调课申请已提交");
    }

    @PostMapping("/change/approve/{id}")
    @ResponseBody
    @OperationLog("审批调课申请")
    public Result<?> changeApprove(@PathVariable Long id, @RequestParam String status,
                                    @RequestParam(required = false) String comment) {
        ScheduleChange change = scheduleChangeService.getById(id);
        if (change != null) {
            change.setApproveStatus(status);
            scheduleChangeService.updateById(change);
        }
        return Result.success();
    }

    @GetMapping("/change/list")
    @ResponseBody
    public Result<PageResult<ScheduleChange>> changeList(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        Page<ScheduleChange> p = scheduleChangeService.page(new Page<>(page, size),
                new LambdaQueryWrapper<ScheduleChange>().orderByDesc(ScheduleChange::getCreatedAt));
        return Result.success(PageResult.of(p));
    }
}
