package com.city.springboot_big_schoolwork.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.city.springboot_big_schoolwork.common.PageResult;
import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.entity.SysUser;
import com.city.springboot_big_schoolwork.entity.SysRole;
import com.city.springboot_big_schoolwork.entity.SysUserRole;
import com.city.springboot_big_schoolwork.mapper.SysUserRoleMapper;
import com.city.springboot_big_schoolwork.service.SysUserService;
import com.city.springboot_big_schoolwork.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String index() {
        return "system/user/index";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<PageResult<Map<String, Object>>> list(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String realName) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(username != null, SysUser::getUsername, username)
                .like(realName != null, SysUser::getRealName, realName)
                .orderByDesc(SysUser::getCreatedAt);
        Page<SysUser> p = sysUserService.page(new Page<>(page, size), wrapper);
        List<Map<String, Object>> records = p.getRecords().stream().map(user -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", user.getId());
            map.put("username", user.getUsername());
            map.put("realName", user.getRealName());
            map.put("status", user.getStatus());
            map.put("createdAt", user.getCreatedAt());
            // 查询角色
            List<String> codes = sysUserRoleMapper.getRoleCodesByUserId(user.getId());
            List<SysRole> roles = sysRoleService.listByIds(
                    sysUserRoleMapper.selectList(
                            new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, user.getId())
                    ).stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
            map.put("roleNames", roles.stream().map(SysRole::getRoleName).collect(Collectors.toList()));
            return map;
        }).collect(Collectors.toList());
        PageResult<Map<String, Object>> pr = new PageResult<>();
        pr.setRecords(records);
        pr.setTotal(p.getTotal());
        pr.setPage((int) p.getCurrent());
        pr.setPages((int) p.getPages());
        return Result.success(pr);
    }

    @GetMapping("/roles")
    @ResponseBody
    public Result<List<SysRole>> allRoles() {
        return Result.success(sysRoleService.list());
    }

    @PostMapping("/save")
    @ResponseBody
    @OperationLog("保存用户信息")
    public Result<?> save(@RequestBody Map<String, Object> params) {
        SysUser user = new SysUser();
        Long id = params.get("id") != null ? Long.valueOf(params.get("id").toString()) : null;
        user.setId(id);
        user.setUsername((String) params.get("username"));
        user.setRealName((String) params.get("realName"));
        user.setStatus(params.get("status") != null ? Integer.valueOf(params.get("status").toString()) : 1);
        @SuppressWarnings("unchecked")
        List<String> roleIds = (List<String>) params.get("roleIds");

        if (id == null) {
            String pw = (String) params.get("password");
            if (pw != null && !pw.isEmpty()) {
                user.setPassword(passwordEncoder.encode(pw));
            }
            sysUserService.save(user);
            id = user.getId();
        } else {
            SysUser existing = sysUserService.getById(id);
            if (existing != null) {
                String pw = (String) params.get("password");
                if (pw != null && !pw.isEmpty() && !pw.startsWith("$2a$")) {
                    user.setPassword(passwordEncoder.encode(pw));
                } else {
                    user.setPassword(existing.getPassword());
                }
                sysUserService.updateById(user);
            }
        }

        // 处理角色关联
        if (roleIds != null && id != null) {
            sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, id));
            for (String rid : roleIds) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(id);
                ur.setRoleId(Long.valueOf(rid));
                sysUserRoleMapper.insert(ur);
            }
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @OperationLog("删除用户")
    public Result<?> delete(@PathVariable Long id) {
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, id));
        sysUserService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Result<Map<String, Object>> get(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("realName", user.getRealName());
        map.put("status", user.getStatus());
        List<Long> roleIds = sysUserRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, id)
        ).stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        map.put("roleIds", roleIds);
        return Result.success(map);
    }
}
