package cn.raths.basic.controller;

import cn.raths.basic.dto.AccountLoginDto;
import cn.raths.basic.dto.PhoneCodeLoginDto;
import cn.raths.basic.dto.WeChatBindDto;
import cn.raths.basic.dto.WechatCodeDto;
import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.service.ILoginService;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.org.domain.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    /**
    * @Title: accountLogin
    * @Description: 账号密码登录
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/14 16:42
    * @Parameters: [accountLoginDto]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PostMapping("/account")
    public AjaxResult accountLogin(@RequestBody AccountLoginDto accountLoginDto)
    {
        try {
            Map<String, Object> map = loginService.accountLogin(accountLoginDto);
            return AjaxResult.getAjaxResult().setResultObj(map);
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    /**
    * @Title: phoneCodeLogin
    * @Description: 短信验证码登录
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/14 16:42
    * @Parameters: [phoneCodeLoginDto]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PostMapping("/phoneCode")
    public AjaxResult phoneCodeLogin(@RequestBody PhoneCodeLoginDto phoneCodeLoginDto)
    {
        try {
            Map<String, Object> map = loginService.phoneCodeLogin(phoneCodeLoginDto);
            return AjaxResult.getAjaxResult().setResultObj(map);
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }


    /**
    * @Title: wechatLogin
    * @Description: 微信登录
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/14 16:44
    * @Parameters: [wechatCodeDto]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PostMapping("/wechat")
    public AjaxResult wechatLogin(@RequestBody WechatCodeDto wechatCodeDto){
        try {
            return loginService.wechatLogin(wechatCodeDto);
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    /**
    * @Title: binder
    * @Description: 微信绑定
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/14 16:44
    * @Parameters: [weChatBindDto]
    * @Return cn.raths.basic.utils.AjaxResult
    */
     @PostMapping("/wechat/binder")
    public AjaxResult binder(@RequestBody WeChatBindDto weChatBindDto){
        try {
            Map<String, Object> map = loginService.binder(weChatBindDto);
            return AjaxResult.getAjaxResult().setResultObj(map);
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
}
