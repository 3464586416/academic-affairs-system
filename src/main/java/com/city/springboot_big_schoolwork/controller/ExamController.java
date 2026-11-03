package com.city.springboot_big_schoolwork.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.springboot_big_schoolwork.common.PageResult;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.entity.Exam;
import com.city.springboot_big_schoolwork.entity.ExamSeat;
import com.city.springboot_big_schoolwork.service.ExamSeatService;
import com.city.springboot_big_schoolwork.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamSeatService examSeatService;

    @GetMapping
    public String index() {
        return "exam/index";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<Exam>> list(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) Long semesterId,
            @RequestParam(required = false) String examType) {
        LambdaQueryWrapper<Exam> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(semesterId != null, Exam::getSemesterId, semesterId)
                .eq(examType != null, Exam::getExamType, examType)
                .eq(Exam::getStatus, 1)
                .orderByAsc(Exam::getExamDate);
        Page<Exam> p = examService.page(new Page<>(page, size), wrapper);
        return Result.success(PageResult.of(p));
    }

    @PostMapping("/save")
    @ResponseBody
    @OperationLog("保存考试安排")
    public Result<?> save(@RequestBody Exam exam) {
        exam.setStatus(1);
        examService.saveOrUpdate(exam);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @OperationLog("删除考试安排")
    public Result<?> delete(@PathVariable Long id) {
        Exam exam = examService.getById(id);
        if (exam != null) {
            exam.setStatus(0);
            examService.updateById(exam);
        }
        return Result.success();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Result<Exam> get(@PathVariable Long id) {
        return Result.success(examService.getById(id));
    }

    // 考场座位管理
    @GetMapping("/seat/{examId}")
    @ResponseBody
    public Result<List<ExamSeat>> getSeats(@PathVariable Long examId) {
        return Result.success(examSeatService.list(
                new LambdaQueryWrapper<ExamSeat>()
                        .eq(ExamSeat::getExamId, examId)
                        .orderByAsc(ExamSeat::getSeatNo)));
    }

    @PostMapping("/seat/save")
    @ResponseBody
    @OperationLog("安排考场座位")
    public Result<?> saveSeats(@RequestBody List<ExamSeat> seats) {
        if (!seats.isEmpty()) {
            Long examId = seats.get(0).getExamId();
            examSeatService.remove(new LambdaQueryWrapper<ExamSeat>().eq(ExamSeat::getExamId, examId));
            examSeatService.saveBatch(seats);
        }
        return Result.success("座位安排成功");
    }
}
