package com.jyw.learn.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyw.learn.base.bean.UserResourceVo;
import com.jyw.learn.base.mapper.UserResourceMapper;
import com.jyw.learn.base.service.IUserResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserResourceServiceImpl implements IUserResourceService {
    @Autowired
    UserResourceMapper mapper;
    @Override
    public List<UserResourceVo> findAll() {
        return mapper.findAll();
    }

    @Override
    public IPage<UserResourceVo> findAllPage() {
        IPage<UserResourceVo> page = new Page<UserResourceVo>(1,2);
        return mapper.findAllPage(page);
    }
}

