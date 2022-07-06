package cn.raths.basic.dto;

import lombok.Data;

@Data
public class RegisterDto {

    // 电话
    private String phone;
    // 图形验证码value
    private String imageCodeValue;
    // 图形验证码key
    private String imageCodeKey;
    // 电话验证码
    private String phoneCode;
    // 密码
    private String password;
    // 第二次输入的密码
    private String passwordRepeat;

    private Integer type;
}
