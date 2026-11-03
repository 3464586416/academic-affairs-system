package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.Exam;
import com.city.springboot_big_schoolwork.mapper.ExamMapper;
import com.city.springboot_big_schoolwork.service.ExamService;
import org.springframework.stereotype.Service;

@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {
}
