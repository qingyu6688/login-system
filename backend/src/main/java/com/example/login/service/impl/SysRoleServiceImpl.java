package com.example.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.login.entity.SysRole;
import com.example.login.mapper.SysRoleMapper;
import com.example.login.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
