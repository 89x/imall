package com.barry.controller;

import com.barry.pojo.Users;
import com.barry.pojo.bo.UserBo;
import com.barry.service.UserService;
import com.barry.utils.CookieUtils;
import com.barry.utils.JSONResult;
import com.barry.utils.JsonUtils;
import com.barry.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/passport")
@Api(value = "注册登录",tags = "用户相关接口")
public class PassportController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户查询",notes = "用户是否存在",httpMethod = "GET")
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
    @ApiOperation(value = "用户注册",notes = "用户注册",httpMethod = "POST")
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody UserBo userBo,HttpServletRequest request,HttpServletResponse response){
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

        if(!password.equals(confirmPwd)){
            return JSONResult.errorMsg("两次密码输入不一致");
        }
        Users userResult =userService.CreateUser(userBo);

        CookieUtils.setCookie(request,response,"coo",
                JsonUtils.objectToJson(userResult),true);
        userResult = setNullProperty(userResult);

        return JSONResult.ok();

    }
    @ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "POST")
    @PostMapping("/Login")
    public JSONResult Login(@RequestBody UserBo userBo,HttpServletRequest request,HttpServletResponse response) throws Exception {
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
        CookieUtils.setCookie(request,response,"coo",
                JsonUtils.objectToJson(userResult),true);

        userResult = setNullProperty(userResult);

        return JSONResult.ok(userResult);

    }
    @ApiOperation(value = "用户退出",notes = "用户退出",httpMethod = "POST")
    @PostMapping("/logout")
    public JSONResult logout(@RequestParam String userId,HttpServletRequest request,HttpServletResponse response) throws Exception {

        CookieUtils.deleteCookie(request,response,"coo");


        return JSONResult.ok();

    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }
}
