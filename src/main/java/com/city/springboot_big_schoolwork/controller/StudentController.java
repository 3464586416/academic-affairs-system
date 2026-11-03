package com.city.springboot_big_schoolwork.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.springboot_big_schoolwork.common.PageResult;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.entity.Student;
import com.city.springboot_big_schoolwork.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String index() {
        return "student/index";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<Student>> list(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) String studentNo,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) String enrollStatus) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(studentNo != null, Student::getStudentNo, studentNo)
                .like(name != null, Student::getName, name)
                .eq(classId != null, Student::getClassId, classId)
                .eq(enrollStatus != null, Student::getEnrollStatus, enrollStatus)
                .eq(Student::getStatus, 1)
                .orderByDesc(Student::getCreatedAt);
        Page<Student> p = studentService.page(new Page<>(page, size), wrapper);
        return Result.success(PageResult.of(p));
    }

    @PostMapping("/save")
    @ResponseBody
    @OperationLog("保存学生信息")
    public Result<?> save(@RequestBody Student student) {
        student.setStatus(1);
        studentService.saveOrUpdate(student);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @OperationLog("删除学生信息")
    public Result<?> delete(@PathVariable Long id) {
        Student student = studentService.getById(id);
        if (student != null) {
            student.setStatus(0);
            studentService.updateById(student);
        }
        return Result.success();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Result<Student> get(@PathVariable Long id) {
        return Result.success(studentService.getById(id));
    }

    @PostMapping("/import")
    @ResponseBody
    @OperationLog("导入学生数据")
    public Result<?> importStudents(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("请选择要导入的文件");
        }
        studentService.importStudents(file);
        return Result.success("导入成功");
    }

    @GetMapping("/export")
    @ResponseBody
    public void exportStudents(HttpServletResponse response,
                               @RequestParam(required = false) String enrollStatus) {
        studentService.exportStudents(response, null);
    }
}
