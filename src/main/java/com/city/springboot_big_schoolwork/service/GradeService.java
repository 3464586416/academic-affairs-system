package com.city.springboot_big_schoolwork.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.city.springboot_big_schoolwork.entity.Grade;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface GradeService extends IService<Grade> {
    BigDecimal calculateGpa(Long studentId, Long semesterId);
    List<Grade> getStudentTranscript(Long studentId);
}
