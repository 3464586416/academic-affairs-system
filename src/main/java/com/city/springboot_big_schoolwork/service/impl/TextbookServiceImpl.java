package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.Textbook;
import com.city.springboot_big_schoolwork.mapper.TextbookMapper;
import com.city.springboot_big_schoolwork.service.TextbookService;
import org.springframework.stereotype.Service;

@Service
public class TextbookServiceImpl extends ServiceImpl<TextbookMapper, Textbook> implements TextbookService {
}
