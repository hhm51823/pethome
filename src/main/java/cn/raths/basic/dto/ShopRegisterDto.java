package cn.raths.basic.dto;

import cn.raths.org.domain.Shop;
import lombok.Data;

@Data
public class ShopRegisterDto {
    // 电话验证码
    private String phoneCode;
    // 邮箱验证码
    private String emailCode;

    private Shop shop;
}
