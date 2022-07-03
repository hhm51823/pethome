package cn.raths.basic.controller;

import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.BaiduAuditUtils;
import cn.raths.org.domain.Shop;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/baiduAudit")
public class BaiduAuditController {

    @PostMapping("/imgCensor")
    public AjaxResult ImgCensor(@RequestBody Shop shop){
        String s = BaiduAuditUtils.ImgCensor(shop.getLogo());
        return AjaxResult.getAjaxResult().setResultObj(s);
    }

}
