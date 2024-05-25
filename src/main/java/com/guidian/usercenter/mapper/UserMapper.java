package com.guidian.usercenter.mapper;

import com.guidian.usercenter.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 27605
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-05-05 07:26:30
* @Entity com.guidian.usercenter.model.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




