package cn.raths.basic.interceptor;

import cn.raths.basic.annotation.PreAuthorize;
import cn.raths.sys.mapper.PermissionMapper;
import cn.raths.user.domain.Logininfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandle;
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


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println("333333333");

        response.setCharacterEncoding("UTF-8");
        // 3.2.设置响应参数类型为JSON
        response.setContentType("application/json;charset=UTF-8");

        // 判断token是否为空
        String token = request.getHeader("token");
        if(!StringUtils.isEmpty(token)){
            // 根据token去redis中拿logininfo并判断是否存在
            Object logininfo = redisTemplate.opsForValue().get(token);

            if (logininfo != null){
                // 重置有效期为30分钟
                redisTemplate.opsForValue().set(token, logininfo, 30, TimeUnit.MINUTES);
                // URL权限拦截
                Logininfo logininfoTmp = new Logininfo();
                BeanUtils.copyProperties(logininfo, logininfoTmp);
                if(logininfoTmp.getType() == 0){
                    HandlerMethod handlerMethod = (HandlerMethod)handler;
                    PreAuthorize preAuthorize = handlerMethod.getMethodAnnotation(PreAuthorize.class);
                    if(preAuthorize != null){
                        String sn = preAuthorize.sn();
                        List<String> ownPermissions = permissionMapper.loadPermissionByLogininfoId(logininfoTmp.getId());
                        if(!ownPermissions.contains(sn)){ // 集合是否包含某一数据
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
        PrintWriter writer = response.getWriter();
        // 设置json对象返回{"success":fasle,"massage":"noLogin"}
        writer.write("{\"success\":false,\"massage\":\"noLogin\"}");
        writer.flush();
        writer.close();
        return false;
    }
}
