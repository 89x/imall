package com.barry.controller;

import com.barry.mapper.UsersMapper;
import com.barry.pojo.Users;
import com.barry.pojo.bo.UserBo;
import com.barry.service.UserService;
import com.barry.utils.JSONResult;
import com.barry.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/passport")
public class PassportController {
    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public JSONResult usernameIsExist(@RequestParam String username){
        if (StringUtils.isBlank(username)){
            return JSONResult.errorMsg("用户名不能为空");
        }
        Boolean  isexist = userService.queryUsernameIsExist(username);
        if (isexist==true){
            return JSONResult.errorMsg("用户名已存在" );
        }
        return JSONResult.ok();
    }
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody UserBo userBo){
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmPwd = userBo.getConfirmPassword();

        if (StringUtils.isBlank(username)||
                StringUtils.isBlank(password)||
                StringUtils.isBlank(confirmPwd)){
            return JSONResult.errorMsg("用户名或者密码不能为空");
        }
        Boolean  isexist = userService.queryUsernameIsExist(username);
        if (isexist==true){
            return JSONResult.errorMsg("用户名已存在" );
        }
        if (password.length()<6){
            return JSONResult.errorMsg("密码长度不能小于6" );
        }
        System.out.println(password);
        System.out.println(confirmPwd);
        if(!password.equals(confirmPwd)){
            return JSONResult.errorMsg("两次密码输入不一致");
        }
        userService.CreateUser(userBo);
        return JSONResult.ok();

    }
    @PostMapping("/Login")
    public JSONResult Login(@RequestBody UserBo userBo) throws Exception {
        String username = userBo.getUsername();
        String password = userBo.getPassword();

        if (StringUtils.isBlank(username)||
                StringUtils.isBlank(password)){
            return JSONResult.errorMsg("用户名或者密码不能为空");
        }
        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (userResult== null){
            return JSONResult.errorMsg("用户名或者密码错误");
        }
        return JSONResult.ok(userResult);

    }
}
