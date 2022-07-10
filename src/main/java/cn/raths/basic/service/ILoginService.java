package cn.raths.basic.service;

import cn.raths.basic.dto.AccountLoginDto;
import cn.raths.basic.dto.PhoneCodeLoginDto;
import cn.raths.basic.dto.WeChatBindDto;
import cn.raths.basic.dto.WechatCodeDto;
import cn.raths.basic.utils.AjaxResult;

import java.util.Map;

public interface ILoginService {
    Map<String, Object> accountLogin(AccountLoginDto accountLoginDto);

    Map<String, Object> phoneCodeLogin(PhoneCodeLoginDto phoneCodeLoginDto);

    void quit(String token);

    AjaxResult wechatLogin(WechatCodeDto wechatCodeDto);

    Map<String, Object> binder(WeChatBindDto weChatBindDto);
}
