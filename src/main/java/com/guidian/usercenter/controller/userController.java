/**
 * @作者 徐振博
 * @创建时间 2024/5/5 8:21
 */
package com.guidian.usercenter.controller;

import com.guidian.usercenter.core.UnifyResponse;
import com.guidian.usercenter.dto.UserLoginDTO;
import com.guidian.usercenter.dto.UserRegisterDTO;
import com.guidian.usercenter.exception.http.ForbiddenException;
import com.guidian.usercenter.exception.http.NotFoundException;
import com.guidian.usercenter.exception.http.ServerErrorException;
import com.guidian.usercenter.model.User;
import com.guidian.usercenter.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserService userService;

    @GetMapping("/logOut")
    public UnifyResponse<Integer> LogOut(HttpServletRequest request){
        if (request == null) {
            throw new ServerErrorException(9999);
        }
        Integer id =  userService.userLogOut(request);
        if (id != 1) {
            throw new NotFoundException(20002);
        }
        return new UnifyResponse(200, "注销成功");
    }

    /**
     * 用户注册返回
     * @param user
     * @return 用户Id
     */
    @PostMapping("/register")
    public UnifyResponse<String> userRegister(@Validated @RequestBody UserRegisterDTO user) {
        if(!user.getPassword1().equals(user.getPassword2())){
            throw new ServerErrorException(20000);
        }
        userService.userRegister(user);
        return new UnifyResponse<>(200, "ok", user.getUserAccount());
    }

    /**
     * 用户登陆
     * @param user
     * @param request
     * @return 用户信息
     */
    @PostMapping("/login")
    public UnifyResponse<User> userLogin(@Validated @RequestBody UserLoginDTO user, HttpServletRequest request) {

        User loginUser =  userService.userLogin(user, request);
        System.out.println(loginUser);

        return new UnifyResponse<>(200, "ok");
    }

    /**
     * 根据Id获取用户信息
     * @param id
     * @param request
     * @return current User
     */
    @GetMapping("/select/{id}")
    public UnifyResponse<User> selectUser(@PathVariable Long id, HttpServletRequest request) {

        if (!isAdmin(request)) {
            throw new ForbiddenException(10005);
        }
        User user = userService.selectUser(id);
        return new UnifyResponse<>(200, user);

    }
    @PostMapping("/delete")
    public UnifyResponse<Boolean> deleteUser(@RequestBody User user, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new ForbiddenException(10005);
        }
        if (user == null) {
            throw new ServerErrorException(20002);
        }

        if (userService.deleteUser(user.getId())) {
            return new UnifyResponse<Boolean>(200,"删除成功", null);
        }
        throw new ServerErrorException(10007);
    }

    /**
     * 当前用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("currentUser")
    public UnifyResponse<User> currentUser(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            throw new NotFoundException(20006);
        }
        User currentUser = (User) user;

        User selectUser = userService.selectUser(currentUser.getId());

        return new UnifyResponse<>(200, selectUser);
    }

    /**
     * 查询所有的用户
     * @param request
     * @return List<用户></>
     */
    @GetMapping("search")
    public UnifyResponse<List<User>> searchUser(HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new ForbiddenException(10005);
        }
        List<User> users = userService.list();

        return new UnifyResponse<>(200, users);

    }


    /**
     * 查询所有的用户
     * @param request
     * @return List<用户></>
     */
    @GetMapping("/list")
    public List<User> searchList(HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new ForbiddenException(10005);
        }
        List<User> users = userService.list();

        return users;

    }

    /**
     * 更新用户
     * @param request
     * @param user
     * @return
     */
    @PostMapping("/updateUser")
    public UnifyResponse<User> updateUser(HttpServletRequest request, @RequestBody User user) {
        if (!isAdmin(request)) {
            throw new ForbiddenException(10005);
        }
        if (user == null) {
            throw new ServerErrorException(20002);
        }

        userService.updateUser(user);
        return new UnifyResponse<>(200, "ok",user);

    }


    /**
     * 是否为管理员，是返回true 否者返回false
     * @param request
     * @return
     */
    private Boolean isAdmin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        User newUser =  userService.getById(user.getId());
        if (newUser.getUserRole() == 0) {
            return false;
        }
        return true;
    }



}
