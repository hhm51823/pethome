package cn.raths.basic.service.impl;

import cn.raths.basic.constant.BaseConstants;
import cn.raths.basic.dto.AccountLoginDto;
import cn.raths.basic.dto.PhoneCodeLoginDto;
import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.service.ILoginService;
import cn.raths.basic.utils.MD5Utils;
import cn.raths.user.domain.Logininfo;
import cn.raths.user.mapper.LogininfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private LogininfoMapper logininfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> accountLogin(AccountLoginDto accountLoginDto) {
        String account = accountLoginDto.getAccount();
        String checkPass = accountLoginDto.getCheckPass();
        String type = accountLoginDto.getType();
        // 判断参数是否为空
        if(StringUtils.isEmpty(account)
            || StringUtils.isEmpty(checkPass)
            || StringUtils.isEmpty(type)){
            throw new BusinessException("参数不能为空!");
        }
        // 账号是否存在
        Logininfo logininfo = logininfoMapper.loadByAccount(account, type);
        if(logininfo == null){
            throw new BusinessException("账号或密码错误!");
        }
        // 密码是否正确
        String password = MD5Utils.encrypByMd5(logininfo.getSalt() + checkPass);
        if(!logininfo.getPassword().equals(password)){
            throw new BusinessException("账号或密码错误!");
        }
        // 生成UUID
        String token = UUID.randomUUID().toString();
        // 存入redis
        redisTemplate.opsForValue().set(token, logininfo, 30, TimeUnit.MINUTES);
        // 将敏感信息置为空
        logininfo.setSalt("");
        logininfo.setPassword("");
        // 将数据保存到map
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("logininfo", logininfo);
        return map;
    }

    @Override
    public Map<String, Object> phoneCodeLogin(PhoneCodeLoginDto phoneCodeLoginDto) {
        String phone = phoneCodeLoginDto.getPhone();
        String phoneCode = phoneCodeLoginDto.getPhoneCode();
        Integer type = phoneCodeLoginDto.getType();
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(phoneCode) || type == null){
            throw new BusinessException("参数不能为空！");
        }

        Logininfo logininfo =logininfoMapper.loadByPhone(phone, type);
        if(logininfo == null){
            throw new BusinessException("该手机号不存在");
        }
        // 拼接redis中电话验证码的key
        String phoneCodeKey = BaseConstants.VerifyCodeConstant.BUSINESS_LOGIN_PREFIX + phone;
        // 从redis中获取值
        Object phoneCodeVaule = redisTemplate.opsForValue().get(phoneCodeKey);
        if (phoneCodeVaule == null){
            throw new BusinessException("验证码已过期！请从新获取！");
        }
        // 分解phoneCodeVaule
        String phoneCodeTmp = phoneCodeVaule.toString().split(":")[1];
        // 判断验证码是否正确
        if(!phoneCodeTmp.equals(phoneCode)){
            throw new BusinessException("验证码错误！请从新输入！");
        }
        // 验证码正确
        // 生成UUID
        String token = UUID.randomUUID().toString();
        // 存入redis
        redisTemplate.opsForValue().set(token, logininfo, 30, TimeUnit.MINUTES);
        // 将敏感信息置为空
        logininfo.setSalt("");
        logininfo.setPassword("");
        // 将数据保存到map
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("logininfo", logininfo);
        return map;
    }
}
