package cn.raths.basic.config;

import cn.raths.basic.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor) // 添加拦截器
                .addPathPatterns("/**") // 拦截路径
                .excludePathPatterns("/login/**") // 放行路径
                .excludePathPatterns("/baiduAudit/**")
                .excludePathPatterns("/fastdfs/**")
                .excludePathPatterns("/fastdfs")
                .excludePathPatterns("/verifyCode/**")
                .excludePathPatterns("/user/register/**")
                .excludePathPatterns("/shop/settlement")
                .excludePathPatterns("/shop/settlement");
    }
}
