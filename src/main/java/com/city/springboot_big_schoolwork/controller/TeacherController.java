package com.city.springboot_big_schoolwork.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.springboot_big_schoolwork.common.PageResult;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.entity.Teacher;
import com.city.springboot_big_schoolwork.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public String index() {
        return "teacher/index";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<Teacher>> list(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) String teacherNo,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long deptId) {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(teacherNo != null, Teacher::getTeacherNo, teacherNo)
                .like(name != null, Teacher::getName, name)
                .eq(deptId != null, Teacher::getDeptId, deptId)
                .eq(Teacher::getStatus, 1)
                .orderByDesc(Teacher::getCreatedAt);
        Page<Teacher> p = teacherService.page(new Page<>(page, size), wrapper);
        return Result.success(PageResult.of(p));
    }

    @PostMapping("/save")
    @ResponseBody
    @OperationLog("保存教师信息")
    public Result<?> save(@RequestBody Teacher teacher) {
        teacher.setStatus(1);
        teacherService.saveOrUpdate(teacher);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @OperationLog("删除教师信息")
    public Result<?> delete(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        if (teacher != null) {
            teacher.setStatus(0);
            teacherService.updateById(teacher);
        }
        return Result.success();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Result<Teacher> get(@PathVariable Long id) {
        return Result.success(teacherService.getById(id));
    }

    @GetMapping("/all")
    @ResponseBody
    public Result<List<Teacher>> all() {
        return Result.success(teacherService.list());
    }
}
