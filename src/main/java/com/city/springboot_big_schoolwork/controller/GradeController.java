package com.city.springboot_big_schoolwork.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.springboot_big_schoolwork.common.PageResult;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.entity.Grade;
import com.city.springboot_big_schoolwork.entity.GradeRemake;
import com.city.springboot_big_schoolwork.service.GradeRemakeService;
import com.city.springboot_big_schoolwork.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private GradeRemakeService gradeRemakeService;

    @GetMapping
    public String index() {
        return "grade/index";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<Grade>> list(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) Long semesterId) {
        LambdaQueryWrapper<Grade> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(studentId != null, Grade::getStudentId, studentId)
                .eq(courseId != null, Grade::getCourseId, courseId)
                .eq(semesterId != null, Grade::getSemesterId, semesterId)
                .eq(Grade::getStatus, 1)
                .orderByDesc(Grade::getCreatedAt);
        Page<Grade> p = gradeService.page(new Page<>(page, size), wrapper);
        return Result.success(PageResult.of(p));
    }

    @PostMapping("/save")
    @ResponseBody
    @OperationLog("保存成绩")
    public Result<?> save(@RequestBody Grade grade) {
        // 自动计算总评成绩 (平时40% + 期末60%)
        if (grade.getRegularScore() != null && grade.getFinalScore() != null) {
            BigDecimal total = grade.getRegularScore().multiply(new BigDecimal("0.4"))
                    .add(grade.getFinalScore().multiply(new BigDecimal("0.6")));
            grade.setTotalScore(total.setScale(1, BigDecimal.ROUND_HALF_UP));
        }
        // 自动计算绩点
        if (grade.getTotalScore() != null) {
            double score = grade.getTotalScore().doubleValue();
            if (score >= 90) grade.setGradePoint(new BigDecimal("4.0"));
            else if (score >= 85) grade.setGradePoint(new BigDecimal("3.7"));
            else if (score >= 82) grade.setGradePoint(new BigDecimal("3.3"));
            else if (score >= 78) grade.setGradePoint(new BigDecimal("3.0"));
            else if (score >= 75) grade.setGradePoint(new BigDecimal("2.7"));
            else if (score >= 72) grade.setGradePoint(new BigDecimal("2.3"));
            else if (score >= 68) grade.setGradePoint(new BigDecimal("2.0"));
            else if (score >= 64) grade.setGradePoint(new BigDecimal("1.5"));
            else if (score >= 60) grade.setGradePoint(new BigDecimal("1.0"));
            else grade.setGradePoint(BigDecimal.ZERO);
        }
        grade.setStatus(1);
        gradeService.saveOrUpdate(grade);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @OperationLog("删除成绩")
    public Result<?> delete(@PathVariable Long id) {
        Grade grade = gradeService.getById(id);
        if (grade != null) {
            grade.setStatus(0);
            gradeService.updateById(grade);
        }
        return Result.success();
    }

    @GetMapping("/gpa/{studentId}")
    @ResponseBody
    public Result<BigDecimal> getGpa(@PathVariable Long studentId,
                                      @RequestParam(required = false) Long semesterId) {
        return Result.success(gradeService.calculateGpa(studentId, semesterId));
    }

    @GetMapping("/transcript/{studentId}")
    @ResponseBody
    public Result<List<Grade>> transcript(@PathVariable Long studentId) {
        return Result.success(gradeService.getStudentTranscript(studentId));
    }

    // 补考/重修管理
    @GetMapping("/remake")
    public String remakeIndex() {
        return "redirect:/";
    }

    @GetMapping("/remake/list")
    @ResponseBody
    public Result<PageResult<GradeRemake>> remakeList(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        Page<GradeRemake> p = gradeRemakeService.page(new Page<>(page, size),
                new LambdaQueryWrapper<GradeRemake>().orderByDesc(GradeRemake::getCreatedAt));
        return Result.success(PageResult.of(p));
    }

    @PostMapping("/remake/save")
    @ResponseBody
    public Result<?> remakeSave(@RequestBody GradeRemake remake) {
        gradeRemakeService.saveOrUpdate(remake);
        return Result.success();
    }
}
