package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.StudentSelection;
import com.city.springboot_big_schoolwork.mapper.StudentSelectionMapper;
import com.city.springboot_big_schoolwork.service.StudentSelectionService;
import org.springframework.stereotype.Service;

@Service
public class StudentSelectionServiceImpl extends ServiceImpl<StudentSelectionMapper, StudentSelection> implements StudentSelectionService {
}
