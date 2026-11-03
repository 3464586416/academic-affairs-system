package com.city.springboot_big_schoolwork.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.city.springboot_big_schoolwork.entity.SysUser;

public interface SysUserService extends IService<SysUser> {
    SysUser findByUsername(String username);
}
