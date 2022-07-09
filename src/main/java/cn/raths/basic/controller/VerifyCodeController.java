package cn.raths.basic.controller;

import cn.raths.basic.dto.EmailRegisterDto;
import cn.raths.basic.dto.PhoneCodeLoginDto;
import cn.raths.basic.dto.RegisterDto;
import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.service.IVerifyCodeService;
import cn.raths.basic.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verifyCode")
public class VerifyCodeController {

    @Autowired
    IVerifyCodeService verifyCodeService;

    @GetMapping ("/image/{imageCodeKey}")
    public AjaxResult getImageCodeBase64Str(@PathVariable(value = "imageCodeKey",required = true) String imageCodeKey)
    {
        try {
            String imageCodeBase64Str = verifyCodeService.getImageCodeBase64Str(imageCodeKey);
            return AjaxResult.getAjaxResult().setResultObj(imageCodeBase64Str);
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    /**
    * @Title: smsCode
    * @Description: 发送短信验证码注册的方法
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/6 16:11
    * @Parameters: [registerDto]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PostMapping ("/smsCode")
    public AjaxResult smsCode(@RequestBody RegisterDto registerDto)
    {
        try {
            verifyCodeService.smsCode(registerDto);
            return AjaxResult.getAjaxResult();
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
    // emailCode
    @PostMapping ("/emailCode")
    public AjaxResult emailCode(@RequestBody EmailRegisterDto emailRegisterDto)
    {
        try {
            verifyCodeService.emailCode(emailRegisterDto);
            return AjaxResult.getAjaxResult();
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }


    @PostMapping ("/smsLoginCode")
    public AjaxResult smsLoginCode(@RequestBody PhoneCodeLoginDto phoneCodeLoginDto)
    {
        try {
            verifyCodeService.smsLoginCode(phoneCodeLoginDto);
            return AjaxResult.getAjaxResult();
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
}
