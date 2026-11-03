package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.ScheduleChange;
import com.city.springboot_big_schoolwork.mapper.ScheduleChangeMapper;
import com.city.springboot_big_schoolwork.service.ScheduleChangeService;
import org.springframework.stereotype.Service;

@Service
public class ScheduleChangeServiceImpl extends ServiceImpl<ScheduleChangeMapper, ScheduleChange> implements ScheduleChangeService {
}
