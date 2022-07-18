package cn.raths.basic.utils;

import cn.raths.basic.jwt.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;

//登录上下文，把登录的相关信息放到这里面，方便调用
// 问题1：
/**
* @Description: 登录上下文工具类，获取当前登录人信息
* @Author: Mr.She
* @Version: 1.0
* @Date:  2022/7/3 11:20
*/
@Slf4j
public class LoginContext {

    /**
    * @Title: getLoginUser
    * @Description: 获取token使用公钥进行解析
     * 问题：当前工具类是没有被Spring管理的，那么我们就不能通过注入的方式获取到配置在yml中的公私钥值，那么这时候我们就需要通过监听器获取
    * @Author: Mr.She
    * @Version: 1.0
    * @Date:  2022/7/3 11:21
    * @Parameters: [javax.servlet.http.HttpServletRequest]
    * @Return java.lang.Object
    */
    public static UserInfo getLoginUser(HttpServletRequest request){
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            return null;
        }
        // 获取公钥，从我们提前初始化好的公私钥对象中获取
        PublicKey publicKey = RsaUtils.getPublicKey(JwtRasHolder.INSTANCE.getJwtRsaPubData());
        // 通过公钥解析token，登陆载荷体
        Payload<UserInfo> payload = JwtUtils.getInfoFromToken(token, publicKey, UserInfo.class);
        if(payload == null){
            log.error("payloadData is null!");
            return null;
        }
        return payload.getUserInfo();
    }

    /**
    * @Title: getEmpployee
    * @Description: TODO
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/17 8:22
    * @Parameters: [request]
    * @Return cn.raths.basic.jwt.UserInfo
    */
    public static UserInfo getEmpployee(HttpServletRequest request){
        UserInfo loginUser = getLoginUser(request);
        // TODO
        return null;
    }


}