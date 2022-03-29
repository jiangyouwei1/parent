package com.jyw.learn.base.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jyw.learn.base.bean.UserResourceVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserResourceMapper {
    List<UserResourceVo> findAll();

    IPage<UserResourceVo> findAllPage(IPage<UserResourceVo> page);
}
