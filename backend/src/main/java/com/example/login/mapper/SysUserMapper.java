package com.example.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.login.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
