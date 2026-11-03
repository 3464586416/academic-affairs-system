package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.TextbookDistribute;
import com.city.springboot_big_schoolwork.mapper.TextbookDistributeMapper;
import com.city.springboot_big_schoolwork.service.TextbookDistributeService;
import org.springframework.stereotype.Service;

@Service
public class TextbookDistributeServiceImpl extends ServiceImpl<TextbookDistributeMapper, TextbookDistribute> implements TextbookDistributeService {
}
