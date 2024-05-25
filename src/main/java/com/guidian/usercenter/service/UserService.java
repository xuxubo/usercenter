package com.guidian.usercenter.service;

import com.guidian.usercenter.dto.UserLoginDTO;
import com.guidian.usercenter.dto.UserRegisterDTO;
import com.guidian.usercenter.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 27605
* @description 针对表【user】的数据库操作Service
* @createDate 2024-05-05 07:26:30
*/
public interface UserService extends IService<User> {

    void userRegister(UserRegisterDTO user);

    User userLogin(UserLoginDTO user, HttpServletRequest request);

    User selectUser(Long id);

    Boolean deleteUser(Long id);

    Integer userLogOut(HttpServletRequest request);

    void updateUser(User user);
}
