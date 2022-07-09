package cn.raths.basic.service.impl;

import cn.raths.basic.constant.BaseConstants;
import cn.raths.basic.dto.EmailRegisterDto;
import cn.raths.basic.dto.PhoneCodeLoginDto;
import cn.raths.basic.dto.RegisterDto;
import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.service.IVerifyCodeService;
import cn.raths.basic.utils.MailUtil;
import cn.raths.basic.utils.SmsUtils;
import cn.raths.basic.utils.StrUtils;
import cn.raths.basic.utils.VerifyCodeUtils;
import cn.raths.org.domain.Employee;
import cn.raths.org.mapper.EmployeeMapper;
import cn.raths.user.domain.Logininfo;
import cn.raths.user.domain.User;
import cn.raths.user.mapper.LogininfoMapper;
import cn.raths.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class VerifyCodeServiceImpl implements IVerifyCodeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private LogininfoMapper logininfoMapper;

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

    /**
    * @Title: smsCode
    * @Description: 用户注册，点击获取手机验证码
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/6 13:02
    * @Parameters: [registerDto]
    * @Return void
    */
    @Override
    public void smsCode(RegisterDto registerDto) {
        String phone = registerDto.getPhone();
        String imageCodeKey = registerDto.getImageCodeKey();
        String imageCodeValue = registerDto.getImageCodeValue();
        // 为空校验
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(imageCodeKey) || StringUtils.isEmpty(imageCodeValue)){
            throw new BusinessException("图形验证码和电话不能为空！");
        }

        // 根据imageCodeKey去Redis中获取值
        String imageCodeValueTmp = redisTemplate.opsForValue().get(imageCodeKey).toString();
        if (imageCodeValueTmp == null){
            throw new BusinessException("验证码已过期！请重新获取！");
        }
        // 如果存在，判断用户输入的值和redis中的值是否相等，不区分大小写
        if(!imageCodeValueTmp.equalsIgnoreCase(imageCodeValue)){
            throw new BusinessException("验证码输入错误！");
        }
        if (registerDto.getType() == 0){
            Employee employee = employeeMapper.loadByPhone(phone);
            if(employee != null){
                throw new BusinessException("该用户已经注册,请直接登录!");
            }
        }else{
            // 判断用户是否存在
            User user = userMapper.loadByPhone(phone);
            if(user != null){
                throw new BusinessException("该用户已经注册,请直接登录!");
            }
        }
        // 根据业务键加电话从redis中获取值并判断是否存在
        // 拼接电话验证码的key
        String smsCodeKey = BaseConstants.VerifyCodeConstant.BUSINESS_REGISTER_PREFIX + phone;
        // 获取redis中的电话验证码的值
        Object smsCodeValueTmp = redisTemplate.opsForValue().get(smsCodeKey);
        // 保存电话验证码
        String smsCode = "";
        if (smsCodeValueTmp == null){
            smsCode = StrUtils.getRandomString(6);
        }else {
            // 获取上一次的时间戳
            Long oldTime = Long.valueOf(smsCodeValueTmp.toString().split(":")[0]);
            // 获取当前时间戳
            Long nowTime = System.currentTimeMillis();
            // 如果两次获取间隔时间小于60秒则提示用户
            if(nowTime - oldTime < 1 * 60 * 1000){
                throw new BusinessException("一分钟后才能再次获取！");
            }
            // 从新赋值给电话验证码
            smsCode = smsCodeValueTmp.toString().split(":")[1];
        }
        // 使用工具类发送手机验证码
        String msg = "您的验证码是：" + smsCode + ",有效期为3分钟。";
        //SmsUtils.SendMsg(phone,msg);
        // 拼接时间戳
        String smsCodeValue = System.currentTimeMillis() + ":" + smsCode;
        // 将数据保存到redis中,设置时效为3分钟
        redisTemplate.opsForValue().set(smsCodeKey, smsCodeValue, 3, TimeUnit.MINUTES);
        System.out.println("手机" + msg);
    }

    @Override
    public void emailCode(EmailRegisterDto emailRegisterDto) {
        String email = emailRegisterDto.getEmail();
        String imageCodeKey = emailRegisterDto.getImageCodeKey();
        String imageCodeValue = emailRegisterDto.getImageCodeValue();
        // 为空校验
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(imageCodeKey) || StringUtils.isEmpty(imageCodeValue)){
            throw new BusinessException("图形验证码和邮箱不能为空！");
        }

        // 根据imageCodeKey去Redis中获取值
        String imageCodeValueTmp = redisTemplate.opsForValue().get(imageCodeKey).toString();
        if (imageCodeValueTmp == null){
            throw new BusinessException("验证码已过期！请重新获取！");
        }
        // 如果存在，判断用户输入的值和redis中的值是否相等，不区分大小写
        if(!imageCodeValueTmp.equalsIgnoreCase(imageCodeValue)){
            throw new BusinessException("验证码输入错误！");
        }
        if (emailRegisterDto.getType() == 0){
            Employee employee = employeeMapper.loadByEmail(email);
            if(employee != null){
                throw new BusinessException("该手机号已经注册");
            }
        }else{
            // 判断用户是否存在
            User user = userMapper.loadByEamil(email);
            if(user != null){
                throw new BusinessException("该邮箱已经注册");
            }
        }

        // 根据业务键加电话从redis中获取值并判断是否存在
        // 拼接email验证码的key
        String emailCodeKey = BaseConstants.VerifyCodeConstant.BUSINESS_REGISTER_PREFIX + email;
        // 获取redis中的邮箱验证码的值
        Object emailCodeValueTmp = redisTemplate.opsForValue().get(emailCodeKey);
        // 保存邮箱验证码
        String emailCode = "";
        if (emailCodeValueTmp == null){
            emailCode = StrUtils.getRandomString(6);
        }else {
            // 获取上一次的时间戳
            Long oldTime = Long.valueOf(emailCodeValueTmp.toString().split(":")[0]);
            // 获取当前时间戳
            Long nowTime = System.currentTimeMillis();
            // 如果两次获取间隔时间小于60秒则提示用户
            if(nowTime - oldTime < 1 * 60 * 1000){
                throw new BusinessException("一分钟后才能再次获取！");
            }
            // 从新赋值给邮箱验证码
            emailCode = emailCodeValueTmp.toString().split(":")[1];
        }
        // 使用工具类发送邮箱验证码
        String msg = "您的注册验证码是：" + emailCode + ",有效期为5分钟。";
        MailUtil.sendSimpleMail(email, "宠物乐园", msg);
        // 拼接时间戳
        String emailCodeValue = System.currentTimeMillis() + ":" + emailCode;
        // 将数据保存到redis中,设置时效为5分钟
        redisTemplate.opsForValue().set(emailCodeKey, emailCodeValue, 5, TimeUnit.MINUTES);
        System.out.println("邮箱" + msg);
    }

    /**
    * @Title: smsLoginCode
    * @Description: 获取短信登录验证码
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/8 17:34
    * @Parameters: [phoneCodeLoginDto]
    * @Return void
    */
    @Override
    public void smsLoginCode(PhoneCodeLoginDto phoneCodeLoginDto) {
        String phone = phoneCodeLoginDto.getPhone();
        String imageCodeKey = phoneCodeLoginDto.getImageCodeKey();
        String imageCodeValue = phoneCodeLoginDto.getImageCodeValue();
        Integer type = phoneCodeLoginDto.getType();
        // 为空校验
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(imageCodeKey) || StringUtils.isEmpty(imageCodeValue)){
            throw new BusinessException("图形验证码和电话不能为空！");
        }

        // 根据imageCodeKey去Redis中获取值
        String imageCodeValueTmp = redisTemplate.opsForValue().get(imageCodeKey).toString();
        if (imageCodeValueTmp == null){
            throw new BusinessException("验证码已过期！请重新获取！");
        }
        // 如果存在，判断用户输入的值和redis中的值是否相等，不区分大小写
        if(!imageCodeValueTmp.equalsIgnoreCase(imageCodeValue)){
            throw new BusinessException("验证码输入错误！");
        }
        Logininfo logininfo =logininfoMapper.loadByPhone(phone, type);

        if(logininfo == null){
            throw new BusinessException("该手机号不存在");
        }
        // 根据业务键加电话从redis中获取值并判断是否存在
        // 拼接电话验证码的key
        String smsCodeKey = BaseConstants.VerifyCodeConstant.BUSINESS_LOGIN_PREFIX + phone;
        // 获取redis中的电话验证码的值
        Object smsCodeValueTmp = redisTemplate.opsForValue().get(smsCodeKey);
        // 保存电话验证码
        String smsCode = "";
        if (smsCodeValueTmp == null){
            smsCode = StrUtils.getRandomString(6);
        }else {
            // 获取上一次的时间戳
            Long oldTime = Long.valueOf(smsCodeValueTmp.toString().split(":")[0]);
            // 获取当前时间戳
            Long nowTime = System.currentTimeMillis();
            // 如果两次获取间隔时间小于60秒则提示用户
            if(nowTime - oldTime < 1 * 60 * 1000){
                throw new BusinessException("一分钟后才能再次获取！");
            }
            // 从新赋值给电话验证码
            smsCode = smsCodeValueTmp.toString().split(":")[1];
        }
        // 使用工具类发送手机验证码
        String msg = "您的登录验证码是：" + smsCode + ",有效期为3分钟。";
        //SmsUtils.SendMsg(phone,msg);
        // 拼接时间戳
        String smsCodeValue = System.currentTimeMillis() + ":" + smsCode;
        // 将数据保存到redis中,设置时效为3分钟
        redisTemplate.opsForValue().set(smsCodeKey, smsCodeValue, 3, TimeUnit.MINUTES);
        System.out.println("手机登录" + msg);
    }
}
