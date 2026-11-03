package com.city.springboot_big_schoolwork.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.springboot_big_schoolwork.common.PageResult;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.entity.SelectionPhase;
import com.city.springboot_big_schoolwork.entity.StudentSelection;
import com.city.springboot_big_schoolwork.service.SelectionPhaseService;
import com.city.springboot_big_schoolwork.service.StudentSelectionService;
import com.city.springboot_big_schoolwork.mapper.StudentSelectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/selection")
public class SelectionController {

    @Autowired
    private SelectionPhaseService selectionPhaseService;

    @Autowired
    private StudentSelectionService studentSelectionService;

    @Autowired
    private StudentSelectionMapper studentSelectionMapper;

    @GetMapping
    public String index() {
        return "selection/index";
    }

    @GetMapping("/phase")
    public String phaseIndex() {
        return "selection/index";
    }

    @GetMapping("/phase/list")
    @ResponseBody
    public Result<PageResult<SelectionPhase>> phaseList(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        Page<SelectionPhase> p = selectionPhaseService.page(new Page<>(page, size),
                new LambdaQueryWrapper<SelectionPhase>().orderByDesc(SelectionPhase::getStartTime));
        return Result.success(PageResult.of(p));
    }

    @PostMapping("/phase/save")
    @ResponseBody
    @OperationLog("保存选课阶段")
    public Result<?> phaseSave(@RequestBody SelectionPhase phase) {
        if (phase.getIsActive() != null && phase.getIsActive() == 1) {
            SelectionPhase resetPhase = new SelectionPhase();
            resetPhase.setIsActive(0);
            selectionPhaseService.update(resetPhase,
                    new LambdaQueryWrapper<SelectionPhase>().eq(SelectionPhase::getIsActive, 1));
        }
        selectionPhaseService.saveOrUpdate(phase);
        return Result.success();
    }

    // 学生选课
    @PostMapping("/select")
    @ResponseBody
    @OperationLog("学生选课")
    public Result<?> selectCourse(@RequestBody StudentSelection selection) {
        selection.setSelectionStatus("已选");
        studentSelectionService.save(selection);
        return Result.success("选课成功");
    }

    @PostMapping("/drop/{id}")
    @ResponseBody
    @OperationLog("学生退课")
    public Result<?> dropCourse(@PathVariable Long id) {
        studentSelectionService.removeById(id);
        return Result.success("退课成功");
    }

    @GetMapping("/my/{studentId}")
    @ResponseBody
    public Result<java.util.List<StudentSelection>> myCourses(@PathVariable Long studentId) {
        return Result.success(studentSelectionService.list(
                new LambdaQueryWrapper<StudentSelection>()
                        .eq(StudentSelection::getStudentId, studentId)
                        .eq(StudentSelection::getSelectionStatus, "已选")));
    }

    // 选课记录列表（带学生姓名和课程名称）
    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<java.util.Map<String, Object>>> list(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        java.util.List<java.util.Map<String, Object>> all = studentSelectionMapper.selectWithDetails();
        long total = all.size();
        long from = (page - 1) * size;
        long to = Math.min(from + size, total);
        java.util.List<java.util.Map<String, Object>> records = (from >= total) ? java.util.Collections.emptyList() : all.subList((int) from, (int) to);
        return Result.success(PageResult.of(total, page, size, records));
    }

    @DeleteMapping("/phase/{id}")
    @ResponseBody
    @OperationLog("删除选课阶段")
    public Result<?> deletePhase(@PathVariable Long id) {
        selectionPhaseService.removeById(id);
        return Result.success();
    }
}
