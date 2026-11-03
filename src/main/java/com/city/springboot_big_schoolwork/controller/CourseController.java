package com.city.springboot_big_schoolwork.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.springboot_big_schoolwork.common.PageResult;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.entity.Course;
import com.city.springboot_big_schoolwork.entity.CoursePrerequisite;
import com.city.springboot_big_schoolwork.service.CoursePrerequisiteService;
import com.city.springboot_big_schoolwork.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CoursePrerequisiteService coursePrerequisiteService;

    @GetMapping
    public String index() {
        return "course/index";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<Course>> list(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) String courseNo,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String courseType) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(courseNo != null, Course::getCourseNo, courseNo)
                .like(courseName != null, Course::getCourseName, courseName)
                .eq(courseType != null, Course::getCourseType, courseType)
                .eq(Course::getStatus, 1)
                .orderByAsc(Course::getCourseNo);
        Page<Course> p = courseService.page(new Page<>(page, size), wrapper);
        return Result.success(PageResult.of(p));
    }

    @GetMapping("/all")
    @ResponseBody
    public Result<List<Course>> all() {
        return Result.success(courseService.list(new LambdaQueryWrapper<Course>()
                .eq(Course::getStatus, 1)));
    }

    @PostMapping("/save")
    @ResponseBody
    @OperationLog("保存课程信息")
    public Result<?> save(@RequestBody Course course) {
        course.setStatus(1);
        courseService.saveOrUpdate(course);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @OperationLog("删除课程")
    public Result<?> delete(@PathVariable Long id) {
        Course course = courseService.getById(id);
        if (course != null) {
            course.setStatus(0);
            courseService.updateById(course);
        }
        return Result.success();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Result<Course> get(@PathVariable Long id) {
        return Result.success(courseService.getById(id));
    }

    // 先修课程管理
    @GetMapping("/{id}/prerequisites")
    @ResponseBody
    public Result<List<CoursePrerequisite>> getPrerequisites(@PathVariable Long id) {
        return Result.success(coursePrerequisiteService.list(
                new LambdaQueryWrapper<CoursePrerequisite>().eq(CoursePrerequisite::getCourseId, id)));
    }

    @PostMapping("/{id}/prerequisites")
    @ResponseBody
    public Result<?> savePrerequisites(@PathVariable Long id, @RequestBody List<Long> prerequisiteIds) {
        coursePrerequisiteService.remove(new LambdaQueryWrapper<CoursePrerequisite>().eq(CoursePrerequisite::getCourseId, id));
        for (Long prereqId : prerequisiteIds) {
            CoursePrerequisite cp = new CoursePrerequisite();
            cp.setCourseId(id);
            cp.setPrerequisiteId(prereqId);
            coursePrerequisiteService.save(cp);
        }
        return Result.success();
    }
}
