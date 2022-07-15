package cn.raths.basic.interceptor;

import cn.raths.basic.annotation.PreAuthorize;
import cn.raths.basic.jwt.JwtUtils;
import cn.raths.basic.jwt.Payload;
import cn.raths.basic.jwt.RsaUtils;
import cn.raths.basic.jwt.UserInfo;
import cn.raths.sys.mapper.PermissionMapper;
import cn.raths.user.domain.Logininfo;
import cn.raths.user.domain.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandle;
import java.security.PublicKey;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.MemoryHandler;

/**
* @Description: 登录拦截器
* @Author: Lynn
* @Version: 1.0
* @Date:  2022/7/7 15:08
*/
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PermissionMapper permissionMapper;

    @Value("${jwt.rsa.pub}")
    private String jwtRsaPub;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println("333333333");

        response.setCharacterEncoding("UTF-8");
        // 3.2.设置响应参数类型为JSON
        response.setContentType("application/json;charset=UTF-8");

        // 判断token是否为空
        String token = request.getHeader("token");
        if(!StringUtils.isEmpty(token)){
            // 2 解析token里面内容
            PublicKey publicKey = RsaUtils.getPublicKey(JwtUtils.class.getClassLoader().getResource(jwtRsaPub).getFile());
            Payload<UserInfo> payload = JwtUtils.getInfoFromToken(token, publicKey, UserInfo.class);
            if(payload != null) {

                UserInfo userInfo = payload.getUserInfo();
                Logininfo logininfo = userInfo.getLogininfo();

                if (logininfo != null) {

                    if (logininfo.getType() == 0) {
                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                        PreAuthorize preAuthorize = handlerMethod.getMethodAnnotation(PreAuthorize.class);
                        if (preAuthorize != null) {
                            String sn = preAuthorize.sn();
                            List<String> permissions = userInfo.getPermissions();
                            if (!permissions.contains(sn)) { // 集合是否包含某一数据
                                PrintWriter writer = response.getWriter();
                                // 设置json对象返回{"success":fasle,"massage":"noLogin"}
                                writer.write("{\"success\":false,\"massage\":\"noPermission\"}");
                                writer.flush();
                                writer.close();
                                return false;
                            }
                        }

                    }
                    return true;
                }
            }
        }
        PrintWriter writer = response.getWriter();
        // 设置json对象返回{"success":fasle,"massage":"noLogin"}
        writer.write("{\"success\":false,\"massage\":\"noLogin\"}");
        writer.flush();
        writer.close();
        return false;
    }
}
