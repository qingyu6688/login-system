package com.example.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.login.entity.SysUser;

public interface SysUserService extends IService<SysUser> {
    SysUser getByUsername(String username);

    SysUser getByMobile(String mobile);
}
