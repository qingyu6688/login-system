package com.example.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.login.entity.SysUser;
import com.example.login.mapper.SysUserMapper;
import com.example.login.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getByUsername(String username) {
        return this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
    }

    @Override
    public SysUser getByMobile(String mobile) {
        return this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getMobile, mobile));
    }
}
