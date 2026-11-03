package com.city.springboot_big_schoolwork.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.springboot_big_schoolwork.common.PageResult;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.entity.SysLog;
import com.city.springboot_big_schoolwork.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/system/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @GetMapping
    public String index() {
        return "system/log/index";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<SysLog>> list(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) String username) {
        LambdaQueryWrapper<SysLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(username != null, SysLog::getUsername, username)
                .orderByDesc(SysLog::getCreatedAt);
        Page<SysLog> p = sysLogService.page(new Page<>(page, size), wrapper);
        return Result.success(PageResult.of(p));
    }
}
