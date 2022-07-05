package cn.raths.basic.service.impl;

import cn.raths.basic.service.IVerifyCodeService;
import cn.raths.basic.utils.StrUtils;
import cn.raths.basic.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerifyCodeServiceImpl implements IVerifyCodeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getImageCodeBase64Str(String imageCodeKey) {
        // 生成4位随机字符串
        String complexRandomString = StrUtils.getComplexRandomString(4);
        // 生成base64编码
        String ImageCodeBase64Str = VerifyCodeUtils.VerifyCode(100, 40, complexRandomString);
        // 放到redis中
        redisTemplate.opsForValue().set(imageCodeKey, complexRandomString, 5, TimeUnit.MINUTES);
        return ImageCodeBase64Str;
    }
}
