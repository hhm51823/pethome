package cn.raths.basic.service;

import cn.raths.basic.dto.AccountLoginDto;
import cn.raths.basic.dto.PhoneCodeLoginDto;

import java.util.Map;

public interface ILoginService {
    Map<String, Object> accountLogin(AccountLoginDto accountLoginDto);

    Map<String, Object> phoneCodeLogin(PhoneCodeLoginDto phoneCodeLoginDto);
}
