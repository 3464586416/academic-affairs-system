package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.Grade;
import com.city.springboot_big_schoolwork.mapper.GradeMapper;
import com.city.springboot_big_schoolwork.service.GradeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Override
    public BigDecimal calculateGpa(Long studentId, Long semesterId) {
        LambdaQueryWrapper<Grade> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Grade::getStudentId, studentId)
                .eq(semesterId != null, Grade::getSemesterId, semesterId)
                .eq(Grade::getStatus, 1);
        List<Grade> grades = list(wrapper);

        if (grades.isEmpty()) return BigDecimal.ZERO;

        BigDecimal totalPoints = BigDecimal.ZERO;
        BigDecimal totalCredits = BigDecimal.ZERO;

        for (Grade g : grades) {
            if (g.getGradePoint() != null && g.getCreditEarned() != null) {
                totalPoints = totalPoints.add(g.getGradePoint().multiply(g.getCreditEarned()));
                totalCredits = totalCredits.add(g.getCreditEarned());
            }
        }

        if (totalCredits.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        return totalPoints.divide(totalCredits, 2, RoundingMode.HALF_UP);
    }

    @Override
    public List<Grade> getStudentTranscript(Long studentId) {
        return list(new LambdaQueryWrapper<Grade>()
                .eq(Grade::getStudentId, studentId)
                .eq(Grade::getStatus, 1)
                .orderByAsc(Grade::getSemesterId));
    }
}
