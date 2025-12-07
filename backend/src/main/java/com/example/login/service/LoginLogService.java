package com.example.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.login.entity.SysLoginLog;

public interface LoginLogService extends IService<SysLoginLog> {
    void recordLoginLog(Long userId, String username, Integer status, String msg, String ip, String userAgent);
}
