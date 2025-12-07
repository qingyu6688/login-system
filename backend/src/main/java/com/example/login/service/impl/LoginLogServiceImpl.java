package com.example.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.login.entity.SysLoginLog;
import com.example.login.mapper.SysLoginLogMapper;
import com.example.login.service.LoginLogService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;

import java.time.LocalDateTime;

@Service
@Slf4j
public class LoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements LoginLogService {

    @Async
    @Override
    public void recordLoginLog(Long userId, String username, Integer status, String msg, String ip,
            String userAgentString) {
        try {
            final UserAgent userAgent = UserAgentUtil.parse(userAgentString);

            SysLoginLog loginLog = new SysLoginLog();
            loginLog.setUserId(userId);
            loginLog.setUsername(username);
            loginLog.setIpAddress(ip);

            String location = com.example.login.util.IpLocationUtils.getRegion(ip);
            loginLog.setLoginLocation(location);

            loginLog.setBrowser(userAgent != null ? userAgent.getBrowser().toString() : "Unknown");
            loginLog.setOs(userAgent != null ? userAgent.getOs().toString() : "Unknown");
            loginLog.setStatus(status);
            loginLog.setMsg(msg);
            loginLog.setLoginTime(LocalDateTime.now());

            this.save(loginLog);
        } catch (Exception e) {
            log.error("Failed to record login log", e);
        }
    }
}
