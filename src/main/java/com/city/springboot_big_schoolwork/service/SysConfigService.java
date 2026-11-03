package com.city.springboot_big_schoolwork.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.city.springboot_big_schoolwork.entity.SysConfig;

public interface SysConfigService extends IService<SysConfig> {
    String getConfigValue(String key);
}
