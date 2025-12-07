package com.example.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.login.entity.SysMenu;
import com.example.login.mapper.SysMenuMapper;
import com.example.login.service.SysMenuService;
import org.springframework.stereotype.Service;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
}
