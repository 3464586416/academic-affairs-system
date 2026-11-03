package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.SysLog;
import com.city.springboot_big_schoolwork.mapper.SysLogMapper;
import com.city.springboot_big_schoolwork.service.SysLogService;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
}
