package com.barry.pojo.bo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(value = "用户对象BO",description = "从客户端由用户传入的数据封装在此entity中")
public class UserBo {

    @ApiModelProperty(value = "用户名",notes = "username",example = "barry",required = true)
    private String username;

    @ApiModelProperty(value = "密码",notes = "password",example = "barry",required = true)
    private String password;

    @ApiModelProperty(value = "确认密码",notes = "confirmPassword",example = "barry",required =false)
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
