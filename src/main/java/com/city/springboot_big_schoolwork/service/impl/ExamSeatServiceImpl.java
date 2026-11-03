package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.ExamSeat;
import com.city.springboot_big_schoolwork.mapper.ExamSeatMapper;
import com.city.springboot_big_schoolwork.service.ExamSeatService;
import org.springframework.stereotype.Service;

@Service
public class ExamSeatServiceImpl extends ServiceImpl<ExamSeatMapper, ExamSeat> implements ExamSeatService {
}
