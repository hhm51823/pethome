package cn.raths.basic.dto;

import lombok.Data;

@Data
public class EmailRegisterDto {

    // 邮箱
    private String email;
    // 图形验证码value
    private String imageCodeValue;
    // 图形验证码key
    private String imageCodeKey;
    // 电话验证码
    private String emailCode;
    // 密码
    private String emailPassword;
    // 第二次输入的密码
    private String emailPasswordRepeat;

    private Integer type;
}
