package cn.raths.basic.listener;


import cn.raths.basic.jwt.JwtRasHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @Description: 当spring容器结束时，我们初始化公私钥文件名称到JwtRasHolder中，方便以后使用
 * @Author: Mr.She
 * @Version: 1.0
 * @Date:  2022/7/3 11:12
 */
@WebListener
@Slf4j
public class SystemInitContextListener implements ServletContextListener {

    @Value("${jwt.rsa.pub}")
    private String jwtRsaPub;
    @Value("${jwt.rsa.pri}")
    private String jwtRsaPrivate;

    // lombok注解帮助我们处理方法异常
    @SneakyThrows
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("System is initing!");
        JwtRasHolder.INSTANCE.setJwtRsaPubData(FileCopyUtils
                .copyToByteArray(this.getClass().getClassLoader().getResourceAsStream(jwtRsaPub)));
        JwtRasHolder.INSTANCE.setJwtRsaPrivateData(FileCopyUtils
                .copyToByteArray(this.getClass().getClassLoader().getResourceAsStream(jwtRsaPrivate)));
        log.info("System is init finished!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("System is Destroyed!");
    }
}