package com.city.springboot_big_schoolwork.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.springboot_big_schoolwork.common.PageResult;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.entity.SysDepartment;
import com.city.springboot_big_schoolwork.service.SysDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/system/dept")
public class SysDepartmentController {

    @Autowired
    private SysDepartmentService sysDepartmentService;

    @GetMapping
    public String index() {
        return "system/dept/index";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<SysDepartment>> list(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) String deptName) {
        LambdaQueryWrapper<SysDepartment> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(deptName != null, SysDepartment::getDeptName, deptName)
                .orderByAsc(SysDepartment::getDeptCode);
        Page<SysDepartment> p = sysDepartmentService.page(new Page<>(page, size), wrapper);
        return Result.success(PageResult.of(p));
    }

    @GetMapping("/all")
    @ResponseBody
    public Result<List<SysDepartment>> all() {
        return Result.success(sysDepartmentService.list());
    }

    @PostMapping("/save")
    @ResponseBody
    @OperationLog("保存院系信息")
    public Result<?> save(@RequestBody SysDepartment dept) {
        sysDepartmentService.saveOrUpdate(dept);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @OperationLog("删除院系信息")
    public Result<?> delete(@PathVariable Long id) {
        sysDepartmentService.removeById(id);
        return Result.success();
    }
}
