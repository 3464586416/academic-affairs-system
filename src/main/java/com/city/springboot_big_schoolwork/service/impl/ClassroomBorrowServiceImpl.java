package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.ClassroomBorrow;
import com.city.springboot_big_schoolwork.mapper.ClassroomBorrowMapper;
import com.city.springboot_big_schoolwork.service.ClassroomBorrowService;
import org.springframework.stereotype.Service;

@Service
public class ClassroomBorrowServiceImpl extends ServiceImpl<ClassroomBorrowMapper, ClassroomBorrow> implements ClassroomBorrowService {
}
