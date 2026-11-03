package com.city.springboot_big_schoolwork.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.city.springboot_big_schoolwork.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
