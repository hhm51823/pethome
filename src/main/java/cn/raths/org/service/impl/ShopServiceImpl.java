package cn.raths.org.service.impl;

import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.utils.BaiduAuditUtils;
import cn.raths.basic.utils.MailUtil;
import cn.raths.org.domain.Employee;
import cn.raths.org.domain.Shop;
import cn.raths.org.domain.ShopAuditLog;
import cn.raths.org.mapper.EmployeeMapper;
import cn.raths.org.mapper.ShopAuditLogMapper;
import cn.raths.org.mapper.ShopMapper;
import cn.raths.org.service.IEmployeeService;
import cn.raths.org.service.IShopService;
import cn.raths.basic.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.security.auth.Subject;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-01
 */
@Service
public class ShopServiceImpl extends BaseServiceImpl<Shop> implements IShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private ShopAuditLogMapper shopAuditLogMapper;



    @Override
    public void settlement(Shop shop) {

        Employee admin = shop.getAdmin();

        // 1判断参数是否为空
        if(StringUtils.isEmpty(shop.getName())){
            throw new BusinessException("店铺名不能为空,请重新输入");
        }
        if(!BaiduAuditUtils.TextCensor(shop.getName())){
            throw new BusinessException("店铺名包含违规信息,请重新输入");
        }
        if(StringUtils.isEmpty(shop.getTel())){
            throw new BusinessException("电话不能为空,请重新输入");
        }
        if(StringUtils.isEmpty(shop.getAddress())){
            throw new BusinessException("地址不能为空,请重新输入");
        }

        if(StringUtils.isEmpty(admin.getUsername())){
            throw new BusinessException("管理员账号不能为空,请重新输入");
        }
        if(!BaiduAuditUtils.TextCensor(admin.getUsername())){
            throw new BusinessException("管理员账号包含违规信息,请重新输入");
        }
        if(StringUtils.isEmpty(admin.getPhone())){
            throw new BusinessException("管理员电话不能为空,请重新输入");
        }
        if(StringUtils.isEmpty(admin.getEmail())){
            throw new BusinessException("管理员邮箱不能为空,请重新输入");
        }
        if(StringUtils.isEmpty(admin.getPassword())){
            throw new BusinessException("管理员密码不能为空,请重新输入");
        }
        if(StringUtils.isEmpty(admin.getComfirmPassword())){
            throw new BusinessException("管理员确认密码不能为空,请重新输入");
        }
        // 2, 两次密码是否一次
        if (!admin.getPassword().equals(admin.getComfirmPassword())){
            throw new BusinessException("两次密码输入不一致,请重新输入");
        }
        // 管理员是否存在，且绑定了店铺
        Employee loadByUsername = employeeMapper.loadByUsername(admin.getUsername());
        if (loadByUsername != null && loadByUsername.getShopId() != null){
            throw new BusinessException("管理员已存在,且绑定了店铺,请重新输入");
        }
        // 管理员是否被禁用
        if (loadByUsername != null && loadByUsername.getState() == 0){
            throw new BusinessException("管理员已被禁用,请重新输入");
        }
        // 店铺是否存在
        Shop loadByName = shopMapper.loadByName(shop.getName());
        if (loadByName != null){
            throw new BusinessException("店铺已入驻,请重新输入");
        }

        // 分别给员工和店铺添加shopid字段和adminid字段
        if (loadByUsername == null){
            loadByUsername = admin;
            employeeMapper.save(admin);
        }
        shop.setAdminId(loadByUsername.getId());
        shopMapper.save(shop);
        loadByUsername.setShopId(shop.getId());
        employeeMapper.update(loadByUsername);
    }

    @Override
    public void reject(ShopAuditLog shopAuditLog) throws MessagingException {
        // 修改店铺状态
        Shop shop = shopMapper.loadById(shopAuditLog.getShopId());
        shop.setState(-1);
        shopMapper.update(shop);
        // 添加日志信息
        shopAuditLog.setAuditId(351L);
        shopAuditLogMapper.save(shopAuditLog);
        // 发送邮件信息
        String to = "1247780087@qq.com";
        String subject = "店铺信息不合规，店铺入驻请求被驳回";
        String contnet = "<a href='http://localhost:8081/#/ShopRegisterRevise?id=" + shopAuditLog.getShopId() + "'>点我修改店铺信息</a>";
        MailUtil.sendHtmlMail(to, subject, contnet);
    }

    @Override
    public void pass(ShopAuditLog shopAuditLog) throws MessagingException {
        // 修改店铺状态
        Shop shop = shopMapper.loadById(shopAuditLog.getShopId());
        shop.setState(2);
        shopMapper.update(shop);
        // 添加日志信息
        shopAuditLog.setAuditId(351L);
        shopAuditLogMapper.save(shopAuditLog);
        // 发送邮件信息
        String to = "1247780087@qq.com";
        String subject = "店铺入驻请求通过，请点击下方链接激活";
        String contnet = "<a href='http://localhost:8080/shop/activation/" + shopAuditLog.getShopId() + "'>点我激活店铺</a>";
        MailUtil.sendHtmlMail(to, subject, contnet);
    }

    @Override
    public void prohibit(ShopAuditLog shopAuditLog) throws MessagingException {
        // 修改店铺状态
        Shop shop = shopMapper.loadById(shopAuditLog.getShopId());
        shop.setState(-2);
        shopMapper.update(shop);
        // 添加日志信息
        shopAuditLog.setAuditId(351L);
        shopAuditLogMapper.save(shopAuditLog);
        // 发送邮件信息
        String to = "1247780087@qq.com";
        String subject = "店铺入驻请求被拒绝";
        String contnet = shopAuditLog.getNote();
        MailUtil.sendSimpleMail(to, subject, contnet);
    }
}
