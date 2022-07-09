package cn.raths.basic.dto;

import lombok.Data;

@Data
public class PhoneCodeLoginDto {

    // 图形验证码value
    private String imageCodeValue;
    // 图形验证码key
    private String imageCodeKey;

    private String phone;
    private String phoneCode;
    private Integer type;

}
