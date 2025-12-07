package com.example.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.login.entity.SysFile;
import com.example.login.mapper.SysFileMapper;
import com.example.login.service.SysFileService;
import org.springframework.stereotype.Service;

@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {
}
