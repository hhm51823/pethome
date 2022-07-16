package cn.raths.basic.listener;

import cn.raths.basic.service.IPermissionScanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
@Slf4j
public class PermissionScanInitListener implements ServletContextListener {

    @Autowired
    private IPermissionScanService permissionScanService;

    // Spring容器创建完成后调用
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 主动打印日志
        log.info("contextInitialized start....");
        // 创建一个新的线程，避免耗费主线程的太多时间，影响项目启动速度
        //new Thread(() -> permissionScanService.scanPermission()).start();
        log.info("contextInitialized stop....");
    }

    // Spring容器销毁时调用
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("contextDestroyed stop....");
    }
}
