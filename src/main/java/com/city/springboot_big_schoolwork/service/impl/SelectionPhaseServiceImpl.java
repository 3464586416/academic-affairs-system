package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.SelectionPhase;
import com.city.springboot_big_schoolwork.mapper.SelectionPhaseMapper;
import com.city.springboot_big_schoolwork.service.SelectionPhaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SelectionPhaseServiceImpl extends ServiceImpl<SelectionPhaseMapper, SelectionPhase> implements SelectionPhaseService {

    @Override
    public SelectionPhase getCurrentPhase() {
        return getOne(new LambdaQueryWrapper<SelectionPhase>()
                .eq(SelectionPhase::getIsActive, 1)
                .le(SelectionPhase::getStartTime, LocalDateTime.now())
                .ge(SelectionPhase::getEndTime, LocalDateTime.now()));
    }
}
