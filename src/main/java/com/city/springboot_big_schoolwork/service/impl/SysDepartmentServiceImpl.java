package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.SysDepartment;
import com.city.springboot_big_schoolwork.mapper.SysDepartmentMapper;
import com.city.springboot_big_schoolwork.service.SysDepartmentService;
import org.springframework.stereotype.Service;

@Service
public class SysDepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements SysDepartmentService {
}
