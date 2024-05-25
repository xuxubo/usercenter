package com.guidian.usercenter.service.impl;

import com.guidian.usercenter.mapper.UserMapper;
import com.guidian.usercenter.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    public UserMapper userMapper;

    @Test
    void addUser() {

        User user = new User();
        user.setUserName("daxia");
        user.setUserAccount("daxia123");

        user.setAvatarUrl("https://portrait.gitee.com/uploads/avatars/user/3031/9094632_DXdaxia_1620607641.png!avatar30 ");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setPhone("111111");
        user.setEmail("222222@qq.com");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);
        int result = userMapper.insert(user);
        Assertions.assertEquals(1, result);
    }
}