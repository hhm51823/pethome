package cn.raths.basic.dto;

import lombok.Data;

@Data
public class WeChatBindDto {
    private String phone;
    private String verifyCode;
    private String accessToken;
    private String openId;
}
