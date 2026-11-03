package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.SysSemester;
import com.city.springboot_big_schoolwork.mapper.SysSemesterMapper;
import com.city.springboot_big_schoolwork.service.SysSemesterService;
import org.springframework.stereotype.Service;

@Service
public class SysSemesterServiceImpl extends ServiceImpl<SysSemesterMapper, SysSemester> implements SysSemesterService {

    @Override
    public SysSemester getCurrentSemester() {
        return getOne(new LambdaQueryWrapper<SysSemester>().eq(SysSemester::getIsCurrent, 1));
    }
}
