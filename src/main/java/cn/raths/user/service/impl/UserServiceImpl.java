package cn.raths.user.service.impl;

import cn.raths.basic.constant.BaseConstants;
import cn.raths.basic.dto.EmailRegisterDto;
import cn.raths.basic.dto.RegisterDto;
import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.utils.MD5Utils;
import cn.raths.basic.utils.StrUtils;
import cn.raths.user.domain.Logininfo;
import cn.raths.user.domain.User;
import cn.raths.user.mapper.LogininfoMapper;
import cn.raths.user.mapper.UserMapper;
import cn.raths.user.service.IUserService;
import cn.raths.basic.service.impl.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-06
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LogininfoMapper logininfoMapper;

    @Override
    public void remove(Long id) {
        User user = super.loadById(id);
        if(user != null){
            logininfoMapper.remove(user.getLogininfoId());
        }
        super.remove(id);
    }

    @Override
    public void update(User user) {
        userInit(user);
        super.update(user);
        Logininfo logininfo = user2Logininfo(user);
        logininfo.setId(user.getLogininfoId());
        logininfoMapper.update(logininfo);
    }

    @Override
    public void save(User user) {
        userInit(user);
        super.save(user);
        Logininfo logininfo = user2Logininfo(user);
        logininfoMapper.save(logininfo);
        user.setLogininfoId(logininfo.getId());
        super.update(user);
    }

    /**
    * @Title: registerPhone
    * @Description: 用户注册业务实现方法
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/6 15:36
    * @Parameters: [registerDto]
    * @Return void
    */
    @Override
    public void registerPhone(RegisterDto registerDto) {
        String phone = registerDto.getPhone();
        String password = registerDto.getPassword();
        String passwordRepeat = registerDto.getPasswordRepeat();
        String phoneCode = registerDto.getPhoneCode();

        // 判断参数是否为空
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(passwordRepeat) || StringUtils.isEmpty(phoneCode)){
            throw new BusinessException("参数不能为空！");
        }
        // 判断两次密码是否一致
        if(!password.equals(passwordRepeat)){
            throw new BusinessException("两次密码不一致！请重新输入！");
        }
        // 判断两次电话是否一致
        // 拼接redis中电话验证码的key
        String phoneCodeKey = BaseConstants.VerifyCodeConstant.BUSINESS_REGISTER_PREFIX + phone;
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
        // 再次判断该用户是否存
        User user = userMapper.loadByPhone(phone);
        if(user != null){
            throw new BusinessException("该用户已经注册,请直接登录!");
        }
        // 将数据封装到User中
        user = RegisterDto2User(registerDto);
        // 添加到数据库
        userMapper.save(user);
        // 将数据复制到logininfo中
        Logininfo logininfo = user2Logininfo(user);
        // 添加到数据库
        logininfoMapper.save(logininfo);
        // 设置user属性
        user.setLogininfoId(logininfo.getId());
        // 修改数据库数据
        userMapper.update(user);

    }

    @Override
    public void registerEmail(EmailRegisterDto emailRegisterDto) {
        String email = emailRegisterDto.getEmail();
        String password = emailRegisterDto.getEmailPassword();
        String passwordRepeat = emailRegisterDto.getEmailPasswordRepeat();
        String emailCode = emailRegisterDto.getEmailCode();

        // 判断参数是否为空
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password) || StringUtils.isEmpty(passwordRepeat) || StringUtils.isEmpty(emailCode)){
            throw new BusinessException("参数不能为空！");
        }
        // 判断两次密码是否一致
        if(!password.equals(passwordRepeat)){
            throw new BusinessException("两次密码不一致！请重新输入！");
        }
        // 判断两次电话是否一致
        // 拼接redis中电话验证码的key
        String emailCodeKey = BaseConstants.VerifyCodeConstant.BUSINESS_REGISTER_PREFIX + email;
        // 从redis中获取值
        Object emailCodeVaule = redisTemplate.opsForValue().get(emailCodeKey);
        if (emailCodeVaule == null){
            throw new BusinessException("验证码已过期！请从新获取！");
        }
        // 分解phoneCodeVaule
        String emailCodeTmp = emailCodeVaule.toString().split(":")[1];
        // 判断验证码是否正确
        if(!emailCodeTmp.equals(emailCode)){
            throw new BusinessException("验证码错误！请从新输入！");
        }
        // 再次判断该用户是否存
        User user = userMapper.loadByEamil(email);
        if(user != null){
            throw new BusinessException("该用户已经注册,请直接登录!");
        }
        // 将数据封装到User中
        user = emailRegisterDto2User(emailRegisterDto);
        // 添加到数据库
        userMapper.save(user);
        // 将数据复制到logininfo中
        Logininfo logininfo = user2Logininfo(user);
        // 添加到数据库
        logininfoMapper.save(logininfo);
        // 设置user属性
        user.setLogininfoId(logininfo.getId());
        // 修改数据库数据
        userMapper.update(user);

    }

    @Override
    public User loadByLogininfoId(Long logininfoId) {
        return userMapper.loadByLogininfoId(logininfoId);
    }

    private User emailRegisterDto2User(EmailRegisterDto emailRegisterDto) {
        User user = new User();
        user.setUsername(emailRegisterDto.getEmail());
        user.setEmail(emailRegisterDto.getEmail());
        // 生成盐值
        String salt = StrUtils.getComplexRandomString(32);
        user.setSalt(salt);
        // 根据MD5生成加密后的密码
        String saltPassword = MD5Utils.encrypByMd5(salt + emailRegisterDto.getEmailPassword());
        user.setPassword(saltPassword);
        return user;
    }

    /**
    * @Title: RegisterDto2User
    * @Description: 根据前端传递的数据初始化user对象
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/6 15:31
    * @Parameters: [registerDto]
    * @Return cn.raths.user.domain.User
    */
    private User RegisterDto2User(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getPhone());
        user.setPhone(registerDto.getPhone());
        // 生成盐值
        String salt = StrUtils.getComplexRandomString(32);
        user.setSalt(salt);
        // 根据MD5生成加密后的密码
        String saltPassword = MD5Utils.encrypByMd5(salt + registerDto.getPassword());
        user.setPassword(saltPassword);
        return user;
    }

    private void userInit(User user) {
        // 生成盐值
        String salt = StrUtils.getComplexRandomString(32);
        user.setSalt(salt);
        // 根据MD5生成加密后的密码
        String saltPassword = MD5Utils.encrypByMd5(salt + user.getPassword());
        user.setPassword(saltPassword);
    }

    /**
    * @Title: user2Logininfo
    * @Description: 根据user对象初始化logininfo对象
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/6 15:31
    * @Parameters: [user]
    * @Return cn.raths.user.domain.Logininfo
    */
    private Logininfo user2Logininfo(User user) {
        Logininfo logininfo = new Logininfo();
        BeanUtils.copyProperties(user, logininfo);
        logininfo.setType(1);
        return logininfo;
    }

}
