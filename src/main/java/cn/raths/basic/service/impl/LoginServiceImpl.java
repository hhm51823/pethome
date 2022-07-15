package cn.raths.basic.service.impl;

import cn.raths.basic.constant.BaseConstants;
import cn.raths.basic.dto.*;
import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.jwt.JwtUtils;
import cn.raths.basic.jwt.RsaUtils;
import cn.raths.basic.jwt.UserInfo;
import cn.raths.basic.service.ILoginService;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.HttpUtil;
import cn.raths.basic.utils.MD5Utils;
import cn.raths.basic.utils.StrUtils;
import cn.raths.sys.domain.Menu;
import cn.raths.sys.mapper.MenuMapper;
import cn.raths.sys.mapper.PermissionMapper;
import cn.raths.user.domain.Logininfo;
import cn.raths.user.domain.User;
import cn.raths.user.domain.Wxuser;
import cn.raths.user.mapper.LogininfoMapper;
import cn.raths.user.mapper.UserMapper;
import cn.raths.user.mapper.WxuserMapper;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private WxuserMapper wxuserMapper;

     @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Value("${jwt.rsa.pri}")
    private String jwtRsaPri;



    /**
    * @Title: accountLogin
    * @Description: 账号密码登录
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/14 16:45
    * @Parameters: [accountLoginDto]
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    */
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

        return getLoginJwtMap(logininfo);
    }

    /**
    * @Title: getLoginJwtMap
    * @Description: 根据登录信息返回用户信息和权限的Jwt
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/14 17:25
    * @Parameters: [logininfo]
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    */
    public Map<String, Object> getLoginJwtMap(Logininfo logininfo){
        // 将数据保存到map
        Map<String, Object> map = new HashMap<>();
        // 保存到UserInfo中
        UserInfo userInfo = new UserInfo();
        if(logininfo.getType() == 0){
            Long logininfoId = logininfo.getId();
            List<String> permissions = permissionMapper.loadPermissionByLogininfoId(logininfoId);
            List<Menu> menus = menuMapper.loadMenuByLogininfoId(logininfoId);

            map.put("permissions",permissions);
            map.put("menus",menus);

            userInfo.setPermissions(permissions);
            userInfo.setMenus(menus);
        }
        userInfo.setLogininfo(logininfo);
        // 获取JWTtoken
        // 获取私钥
        PrivateKey privateKey = RsaUtils.getPrivateKey(JwtUtils.class.getClassLoader().getResource(jwtRsaPri).getFile());
        String token = JwtUtils.generateTokenExpireInMinutes(userInfo, privateKey, 30);

        logininfo.setSalt("");
        logininfo.setPassword("");
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

        return getLoginJwtMap(logininfo);
    }


    /**
    * @Title: wechatLogin
    * @Description: 微信登录
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/10 16:28
    * @Parameters: [wechatCodeDto]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @Override
    public AjaxResult wechatLogin(@RequestBody WechatCodeDto wechatCodeDto) {
        String code = wechatCodeDto.getCode();
        if(StringUtils.isEmpty(code)){
            throw new BusinessException("参数不能为空!");
        }
        // 拼接获取token和openID的URL地址
        String getTokenUrl = BaseConstants.wechatConstant.BUSINESS_GETTOKEN_URL
                .replace("APPID",BaseConstants.wechatConstant.APPID)
                .replace("SECRET",BaseConstants.wechatConstant.SECRET)
                .replace("CODE",code);
        // 发送get请求
        String jsonStr = HttpUtil.httpGet(getTokenUrl);
        // 将json字符串转换为JSONObject对象
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        // 根据openid查询wxuser
        Wxuser wxuser = wxuserMapper.loadByOpenId(jsonObject.getString("openid"));
        // 判断wxuser是否存在
        if(wxuser != null && wxuser.getUserId() != null){ // wxuser存在且绑定了userID
            Logininfo logininfo = logininfoMapper.loadByUserId(wxuser.getUserId());

            // 将数据放入jwt
            Map<String, Object> loginJwtMap = getLoginJwtMap(logininfo);
            return AjaxResult.getAjaxResult().setResultObj(loginJwtMap);

        }
        // 如果不存在则返回失败result并返回两个参数
        StringBuffer prpamStr = new StringBuffer().append("?accessToken=")
                .append(jsonObject.getString("access_token"))
                .append("&openId=")
                .append(jsonObject.getString("openid"));
        return AjaxResult.error().setMessage("nobinder").setResultObj(prpamStr);
    }

    /**
    * @Title: binder
    * @Description: 微信登录注册绑定
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/10 17:56
    * @Parameters: [weChatBindDto]
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    */
    @Override
    @Transactional
    public Map<String, Object> binder(WeChatBindDto weChatBindDto) {
        String accessToken = weChatBindDto.getAccessToken();
        String openId = weChatBindDto.getOpenId();
        String verifyCode = weChatBindDto.getVerifyCode();
        String phone = weChatBindDto.getPhone();
        // 为空校验
        if(StringUtils.isEmpty(accessToken)
            || StringUtils.isEmpty(openId)
            || StringUtils.isEmpty(verifyCode)
            || StringUtils.isEmpty(phone)){
            throw new BusinessException("参数不能为空");
        }
        // 验证码校验
        // 拼接redis中电话验证码的key
        String phoneCodeKey = BaseConstants.VerifyCodeConstant.BUSINESS_BINDER_PREFIX + phone;
        // 从redis中获取值
        Object phoneCodeVaule = redisTemplate.opsForValue().get(phoneCodeKey);
        if (phoneCodeVaule == null){
            throw new BusinessException("验证码已过期！请从新获取！");
        }
        // 分解phoneCodeVaule
        String phoneCodeTmp = phoneCodeVaule.toString().split(":")[1];
        // 判断验证码是否正确
        if(!phoneCodeTmp.equals(verifyCode)){
            throw new BusinessException("验证码错误！请从新输入！");
        }
        User user = userMapper.loadByPhone(phone);
        Logininfo logininfo = null;
        // 判断用户之前是否注册过
        if(user == null){
            // 根据手机号初始化user
            user = phone2User(phone);
            // 初始化logininfo
            logininfo = user2Logininfo(user);
            // 添加logininfo
            logininfoMapper.save(logininfo);
            // 设置user的logininfo_id
            user.setLogininfoId(logininfo.getId());
            // 添加user
            userMapper.save(user);
        }else{
            // 根据userID获取logininfo
            logininfo = logininfoMapper.loadByUserId(user.getId());
        }
        // 拼接获取微信基本信息的连接
        String getWxuserUrl = BaseConstants.wechatConstant.BUSINESS_GETWXUSER_URL
                .replace("ACCESS_TOKEN", accessToken)
                .replace("OPENID", openId);
        String jsonStr = HttpUtil.httpGet(getWxuserUrl);
        Wxuser wxuser = JSONObject.parseObject(jsonStr, Wxuser.class);
        wxuser.setUserId(user.getId());
        wxuserMapper.save(wxuser);

        // 将数据放入jwt
        return getLoginJwtMap(logininfo);
    }

    /**
    * @Title: phone2User
    * @Description: 根据电话号码初始化user
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/14 17:29
    * @Parameters: [phone]
    * @Return cn.raths.user.domain.User
    */
    private User phone2User(String phone) {
        User user = new User();
        user.setUsername(phone);
        user.setPhone(phone);
        // 生成盐值
        String salt = StrUtils.getComplexRandomString(32);
        user.setSalt(salt);
        // 根据MD5生成加密后的密码
        String saltPassword = MD5Utils.encrypByMd5(salt + "123456");
        user.setPassword(saltPassword);
        return user;
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
