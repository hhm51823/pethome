package cn.raths.basic.controller;

import cn.raths.basic.dto.AccountLoginDto;
import cn.raths.basic.dto.PhoneCodeLoginDto;
import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.service.ILoginService;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.org.domain.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ILoginService loginService;

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
}
