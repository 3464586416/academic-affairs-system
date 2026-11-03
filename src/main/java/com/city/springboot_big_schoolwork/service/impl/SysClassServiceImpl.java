package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.SysClass;
import com.city.springboot_big_schoolwork.mapper.SysClassMapper;
import com.city.springboot_big_schoolwork.service.SysClassService;
import org.springframework.stereotype.Service;

@Service
public class SysClassServiceImpl extends ServiceImpl<SysClassMapper, SysClass> implements SysClassService {
}
