package com.city.springboot_big_schoolwork.controller;

import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.entity.SysConfig;
import com.city.springboot_big_schoolwork.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/system/config")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @GetMapping
    public String index() {
        return "system/config/index";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<List<SysConfig>> list() {
        return Result.success(sysConfigService.list());
    }

    @PostMapping("/save")
    @ResponseBody
    @OperationLog("保存系统配置")
    public Result<?> save(@RequestBody SysConfig config) {
        sysConfigService.saveOrUpdate(config);
        return Result.success();
    }

    @GetMapping("/value/{key}")
    @ResponseBody
    public Result<String> getValue(@PathVariable String key) {
        return Result.success(sysConfigService.getConfigValue(key));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @OperationLog("删除系统配置")
    public Result<?> delete(@PathVariable Long id) {
        sysConfigService.removeById(id);
        return Result.success();
    }
}
