package cn.raths.basic.constant;

/**
* @Description: 公共的常量类
* @Author: Lynn
* @Version: 1.0
* @Date:  2022/7/6 11:03  
*/
public class BaseConstants {
    
    /**
    * @Description: 短信验证常量
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/6 11:03  
    */
    public class SmsConstant {

            public static final String SMS_UID = "lynnming";

            public static final String SMS_KEY = "559C6A76A1810C56CF5A6612CCBC8322";
    }

    /**
    * @Description: 验证code常量类
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/6 11:07
    */
    public class VerifyCodeConstant {
        public static final String BUSINESS_REGISTER_PREFIX = "regisser:";
        public static final String BUSINESS_LOGIN_PREFIX = "login:";
        public static final String BUSINESS_BINDER_PREFIX = "binder:";
    }

    /**
    * @Description: 微信登录的常量类
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/10 17:08
    */

    public class wechatConstant {
        // 根据code获取token的地址
        public static final String BUSINESS_GETTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        // 获取微信用户基本信息的连接
        public static final String BUSINESS_GETWXUSER_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

        public static final String APPID = "wxd853562a0548a7d0";
        public static final String SECRET = "4a5d5615f93f24bdba2ba8534642dbb6";

    }
    
}
