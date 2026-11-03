package com.city.springboot_big_schoolwork.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.springboot_big_schoolwork.common.PageResult;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.entity.SysClass;
import com.city.springboot_big_schoolwork.service.SysClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/system/class")
public class SysClassController {

    @Autowired
    private SysClassService sysClassService;

    @GetMapping
    public String index() {
        return "system/class/index";
    }

    @GetMapping("/all")
    @ResponseBody
    public Result<?> all() {
        LambdaQueryWrapper<SysClass> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysClass::getCreatedAt);
        return Result.success(sysClassService.list(wrapper));
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<SysClass>> list(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) Long deptId) {
        LambdaQueryWrapper<SysClass> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(className != null, SysClass::getClassName, className)
                .eq(deptId != null, SysClass::getDeptId, deptId)
                .orderByDesc(SysClass::getCreatedAt);
        Page<SysClass> p = sysClassService.page(new Page<>(page, size), wrapper);
        return Result.success(PageResult.of(p));
    }

    @PostMapping("/save")
    @ResponseBody
    @OperationLog("保存班级信息")
    public Result<?> save(@RequestBody SysClass sysClass) {
        sysClassService.saveOrUpdate(sysClass);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @OperationLog("删除班级信息")
    public Result<?> delete(@PathVariable Long id) {
        sysClassService.removeById(id);
        return Result.success();
    }
}
