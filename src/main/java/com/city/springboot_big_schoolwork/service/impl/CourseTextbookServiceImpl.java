package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.CourseTextbook;
import com.city.springboot_big_schoolwork.mapper.CourseTextbookMapper;
import com.city.springboot_big_schoolwork.service.CourseTextbookService;
import org.springframework.stereotype.Service;

@Service
public class CourseTextbookServiceImpl extends ServiceImpl<CourseTextbookMapper, CourseTextbook> implements CourseTextbookService {
}
