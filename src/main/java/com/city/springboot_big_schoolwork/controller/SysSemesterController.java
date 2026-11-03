package com.city.springboot_big_schoolwork.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.springboot_big_schoolwork.common.PageResult;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.entity.SysSemester;
import com.city.springboot_big_schoolwork.service.SysSemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/system/semester")
public class SysSemesterController {

    @Autowired
    private SysSemesterService sysSemesterService;

    @GetMapping
    public String index() {
        return "system/semester/index";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<SysSemester>> list(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        LambdaQueryWrapper<SysSemester> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysSemester::getCreatedAt);
        Page<SysSemester> p = sysSemesterService.page(new Page<>(page, size), wrapper);
        return Result.success(PageResult.of(p));
    }

    @GetMapping("/current")
    @ResponseBody
    public Result<SysSemester> getCurrent() {
        return Result.success(sysSemesterService.getCurrentSemester());
    }

    @GetMapping("/all")
    @ResponseBody
    public Result<List<SysSemester>> all() {
        return Result.success(sysSemesterService.list());
    }

    @PostMapping("/save")
    @ResponseBody
    @OperationLog("保存学期信息")
    public Result<?> save(@RequestBody SysSemester semester) {
        if (semester.getIsCurrent() != null && semester.getIsCurrent() == 1) {
            LambdaQueryWrapper<SysSemester> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysSemester::getIsCurrent, 1);
            SysSemester current = sysSemesterService.getOne(wrapper);
            if (current != null) {
                current.setIsCurrent(0);
                sysSemesterService.updateById(current);
            }
        }
        sysSemesterService.saveOrUpdate(semester);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @OperationLog("删除学期信息")
    public Result<?> delete(@PathVariable Long id) {
        sysSemesterService.removeById(id);
        return Result.success();
    }
}
