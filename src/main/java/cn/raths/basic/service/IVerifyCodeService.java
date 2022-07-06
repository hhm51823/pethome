package cn.raths.basic.service;

import cn.raths.basic.dto.EmailRegisterDto;
import cn.raths.basic.dto.RegisterDto;
import org.springframework.stereotype.Service;


public interface IVerifyCodeService {

    String getImageCodeBase64Str(String imageCodeKey);

    void smsCode(RegisterDto registerDto);

    void emailCode(EmailRegisterDto emailRegisterDto);
}
