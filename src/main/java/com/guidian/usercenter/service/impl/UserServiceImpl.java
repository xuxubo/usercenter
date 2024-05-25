package com.guidian.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guidian.usercenter.dto.UserLoginDTO;
import com.guidian.usercenter.dto.UserRegisterDTO;
import com.guidian.usercenter.exception.http.NotFoundException;
import com.guidian.usercenter.exception.http.ServerErrorException;
import com.guidian.usercenter.exception.http.UnAuthenticatedException;
import com.guidian.usercenter.model.User;
import com.guidian.usercenter.service.UserService;
import com.guidian.usercenter.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 27605
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-05-05 07:26:30
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Override
    public List<User> list() {
        return super.list();
    }

    @Autowired
    private UserMapper userMapper;

    private final String SALT = "Xuzhenbo";

    /**
     * 用户注册
     *
     * @param user
     * @author xu
     */
    @Override
    public void userRegister(UserRegisterDTO user) {
        if (!user.getPassword1().equals(user.getPassword2())) {
            throw new ServerErrorException(20000);
        }
        // 1.4. 账户不包含特殊字符
        String validRule = "[`~!@#$%^&*()+=|{}':;',.<>/?~！@#￥%……&*（）——+ | {}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validRule).matcher(user.getUserAccount());
        // 如果包含非法字符，则返回
        if (matcher.find()) {
            throw new ServerErrorException(20000);
        }


        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", user.getUserAccount());
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new ServerErrorException(20001);
        }

        String verifyPassword = DigestUtils.md5DigestAsHex((SALT +
                user.getPassword1()).getBytes(StandardCharsets.UTF_8));
        User userSave = new User();
        userSave.setUserAccount(user.getUserAccount());
        userSave.setUserPassword(verifyPassword);
        int res = userMapper.insert(userSave);
        if (res < 0) {
            throw new ServerErrorException(20000);
        }
    }

    /**
     * 用户登陆
     * @param user
     * @param request
     * @return
     */
    @Override
    public User userLogin(UserLoginDTO user, HttpServletRequest request) {


        String encodePassword = DigestUtils.md5DigestAsHex((SALT +
                user.getUserPassword()).getBytes(StandardCharsets.UTF_8));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 这里存在bug：会把逻辑删除的用户查出来
        queryWrapper.eq("userAccount", user.getUserAccount());
        queryWrapper.eq("userPassword", encodePassword);
        User selectUser = userMapper.selectOne(queryWrapper);
        if (selectUser == null) {

            log.info("user login failed, userAccount Cannot match userPassword");
            throw new UnAuthenticatedException(20003);
        }

        User newUser = new User();
        newUser.setId(selectUser.getId());
        newUser.setUserName(selectUser.getUserName());
        newUser.setUserAccount(selectUser.getUserAccount());
        newUser.setAvatarUrl(selectUser.getAvatarUrl());
        newUser.setGender(selectUser.getGender());
        newUser.setPhone(selectUser.getPhone());
        newUser.setEmail(selectUser.getEmail());
        newUser.setUserStatus(selectUser.getUserStatus());
        newUser.setCreateTime(selectUser.getCreateTime());

        // 4.记录用户的登录态（session），将其存到服务器上
        request.getSession().setAttribute("user", newUser);
        // 5.返回脱敏后的用户信息
        return newUser;
    }

    /**
     * 根据Id获取用户信息
     * @param id
     * @return
     */
    @Override
    public User selectUser(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null){
            throw new NotFoundException(20002);
        }
        return user;
    }


    @Override
    public Boolean deleteUser(Long id) {
        return userMapper.deleteById(id) > 0;
    }
    /**
     * 登出
     * @param
     * @return
     */
    @Override
    public Integer userLogOut(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return 1;
    }

    @Override
    public void updateUser(User user) {

        if(user == null){
            throw new ServerErrorException(20002);
        }
        int count = userMapper.updateById(user);
        if (count <= 0) {
            throw new ServerErrorException(20002);
        }

    }

}




