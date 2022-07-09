package cn.raths.org.service.impl;

import cn.raths.basic.constant.BaseConstants;
import cn.raths.basic.dto.ShopRegisterDto;
import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.service.impl.BaseServiceImpl;
import cn.raths.basic.utils.*;
import cn.raths.org.domain.Employee;
import cn.raths.org.domain.Shop;
import cn.raths.org.domain.ShopAuditLog;
import cn.raths.org.mapper.EmployeeMapper;
import cn.raths.org.mapper.ShopAuditLogMapper;
import cn.raths.org.mapper.ShopMapper;
import cn.raths.org.service.IShopService;
import cn.raths.org.vo.ShopVo;
import cn.raths.user.domain.Logininfo;
import cn.raths.user.domain.User;
import cn.raths.user.mapper.LogininfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private LogininfoMapper logininfoMapper;

     @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ShopAuditLogMapper shopAuditLogMapper;


    /**
    * @Title: settlement
    * @Description: 店铺入驻
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/6 18:17
    * @Parameters: [shop]
    * @Return void
    */
    @Override
    public void settlement(ShopRegisterDto shopRegisterDto) {
        Shop shop = shopRegisterDto.getShop();
        Employee admin = shop.getAdmin();
        String phoneCode = shopRegisterDto.getPhoneCode();
        String emailCode = shopRegisterDto.getEmailCode();

        if(StringUtils.isEmpty(emailCode)){
            throw new BusinessException("请输入邮箱验证码");
        }
        if(StringUtils.isEmpty(phoneCode)){
            throw new BusinessException("请输入手机验证码");
        }
        // 判断验证码是否正确
        // 根据业务键加电话从redis中获取值并判断是否存在
        // 拼接email验证码的key
        String emailCodeKey = BaseConstants.VerifyCodeConstant.BUSINESS_REGISTER_PREFIX + shop.getAdmin().getEmail();
        Object emailCodeTmp = redisTemplate.opsForValue().get(emailCodeKey);
        if(emailCodeTmp == null){
            throw new BusinessException("邮箱验证码已过期");
        }
        if(!emailCode.equals(emailCodeTmp.toString().split(":")[1])){
            throw new BusinessException("邮箱验证码错误");
        }
        // 拼接phone验证码的key
        String phoneCodeKey = BaseConstants.VerifyCodeConstant.BUSINESS_REGISTER_PREFIX + shop.getAdmin().getPhone();
        Object phoneCodeTmp = redisTemplate.opsForValue().get(phoneCodeKey);
        if(phoneCodeTmp == null){
            throw new BusinessException("手机验证码已过期");
        }
        if(!emailCode.equals(emailCodeTmp.toString().split(":")[1])){
            throw new BusinessException("手机验证码错误");
        }
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
            throw new BusinessException("管理员密码确认不能为空,请重新输入");
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
        if (loadByName != null && shop.getId() == null){
            throw new BusinessException("店铺已入驻,请重新输入");
        }

        // 分别给员工和店铺添加shopid字段和adminid字段
        if (loadByUsername == null){
            adminInit(admin);
            employeeMapper.save(admin);
            Logininfo logininfo = admin2Logininfo(admin);
            logininfoMapper.save(logininfo);
            admin.setLogininfoId(logininfo.getId());
            employeeMapper.update(admin);
            loadByUsername = admin;
        }
        shop.setAdminId(loadByUsername.getId());
        shopMapper.save(shop);

        loadByUsername.setShopId(shop.getId());
        employeeMapper.update(loadByUsername);
    }

    private void adminInit(Employee admin) {
        // 生成盐值
        String salt = StrUtils.getComplexRandomString(32);
        admin.setSalt(salt);
        // 根据MD5生成加密后的密码
        String saltPassword = MD5Utils.encrypByMd5(salt + admin.getPassword());
        admin.setPassword(saltPassword);
    }

    /**
     * @Title: admin2Logininfo
     * @Description: 根据user对象初始化logininfo对象
     * @Author: Lynn
     * @Version: 1.0
     * @Date:  2022/7/6 15:31
     * @Parameters: [user]
     * @Return cn.raths.user.domain.Logininfo
     */
    private Logininfo admin2Logininfo(Employee admin) {
        Logininfo logininfo = new Logininfo();
        BeanUtils.copyProperties(admin, logininfo);
        logininfo.setType(0);
        return logininfo;
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

    @Override
    public String activation(Long id) {
        Shop shop = shopMapper.loadById(id);
        shop.setState(1);
        shopMapper.update(shop);
        return "激活成功";
    }

    @Override
    public void exportExcel(HttpServletResponse response) {
        List<Shop> shops = shopMapper.loadAll();
        ExcelUtil.exportExcel(shops, null, "店铺信息", Shop.class, "店铺信息.xlsx", response);

    }

    @Override
    public void importExcel(MultipartFile file) {
        List<Shop> shops = ExcelUtil.importExcel(file, 0, 1, Shop.class);
        shopMapper.batchSave(shops);
    }

    /**
    * @Title: echarts
    * @Description: 数据统计
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/2 20:54
    * @Parameters: []
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    */
    @Override
    public Map<String, Object> echarts() {
        List<ShopVo> shopVos = shopMapper.echarts();
        List<String> x = shopVos.stream().map(ShopVo::getState)
                .map(a -> a == 0 ? "待审核" : a == 1 ? "已审核" : a == 2 ? "待激活" : a == -1 ? "驳回" : "拒绝")
                .collect(Collectors.toList());
        List<Integer> y = shopVos.stream().map(ShopVo::getCount).collect(Collectors.toList());
        HashMap<String, Object> map = new HashMap<>();
        map.put("x", x);
        map.put("y", y);
        return map;
    }

    @Override
    public Shop loadById(Long id){
        Shop shop = shopMapper.loadById(id);
        if(shop.getAdminId() != null){
            Employee employee = employeeMapper.loadById(shop.getAdminId());
            shop.setAdmin(employee);
        }
        return shop;
    }
}
