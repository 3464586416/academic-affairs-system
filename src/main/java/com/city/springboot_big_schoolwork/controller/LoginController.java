package com.city.springboot_big_schoolwork.controller;

import com.city.springboot_big_schoolwork.common.Result;
import com.city.springboot_big_schoolwork.entity.SysUser;
import com.city.springboot_big_schoolwork.mapper.SysUserRoleMapper;
import com.city.springboot_big_schoolwork.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @PostMapping("/login")
    public Result<?> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        SysUser user = sysUserService.findByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return Result.error("密码错误");
        }
        if (user.getStatus() != 1) {
            return Result.error("账号已被禁用");
        }
        session.setAttribute("user", user);
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());

        List<String> roles = sysUserRoleMapper.getRoleCodesByUserId(user.getId());
        session.setAttribute("roles", roles);
        session.setAttribute("roleCodes", roles);
        user.setRoles(roles);

        return Result.success(user);
    }

    @PostMapping("/logout")
    public Result<?> logout(HttpSession session) {
        session.invalidate();
        return Result.success();
    }

    @GetMapping("/currentUser")
    public Result<?> currentUser(HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("user");
        if (user != null) {
            List<String> roles = (List<String>) session.getAttribute("roles");
            if (roles == null) {
                roles = sysUserRoleMapper.getRoleCodesByUserId(user.getId());
                session.setAttribute("roles", roles);
            }
            user.setRoles(roles);
            return Result.success(user);
        }
        return Result.error(401, "未登录");
    }
}
