package com.jyw.learn.base.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jyw.learn.base.bean.UserResourceVo;

import java.util.List;

public interface IUserResourceService {
    List<UserResourceVo> findAll();

    IPage<UserResourceVo> findAllPage();
}
