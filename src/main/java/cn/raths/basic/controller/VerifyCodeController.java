package cn.raths.basic.controller;

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
}
