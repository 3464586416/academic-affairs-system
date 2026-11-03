package com.city.springboot_big_schoolwork.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.city.springboot_big_schoolwork.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    @Select("SELECT r.role_code FROM sys_role r " +
            "INNER JOIN sys_user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<String> getRoleCodesByUserId(Long userId);
}
