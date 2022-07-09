package cn.raths.basic.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("333333333");
        // 判断token是否为空
        String token = request.getHeader("token");
        if(!StringUtils.isEmpty(token)){
            // 根据token去redis中拿logininfo并判断是否存在
            Object logininfo = redisTemplate.opsForValue().get(token);
            if (logininfo != null){
                // 重置有效期为30分钟
                redisTemplate.opsForValue().set(token, logininfo, 30, TimeUnit.MINUTES);
                return true;
            }
        }
        PrintWriter writer = response.getWriter();
        response.setCharacterEncoding("UTF-8");
        // 3.2.设置响应参数类型为JSON
        response.setContentType("application/json;charset=UTF-8");
        // 设置json对象返回{"success":fasle,"massage":"noLogin"}
        writer.write("{\"success\":false,\"massage\":\"noLogin\"}");
        writer.flush();
        writer.close();
        return false;
    }
}
