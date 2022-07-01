package cn.raths.org.service.impl;

import cn.raths.basic.exception.BusinessException;
import cn.raths.org.domain.Employee;
import cn.raths.org.domain.Shop;
import cn.raths.org.mapper.EmployeeMapper;
import cn.raths.org.mapper.ShopMapper;
import cn.raths.org.service.IEmployeeService;
import cn.raths.org.service.IShopService;
import cn.raths.basic.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public void settlement(Shop shop) {

        Employee admin = shop.getAdmin();

        // 1判断参数是否为空
        if(StringUtils.isEmpty(shop.getName())){
            throw new BusinessException("店铺名不能为空,请重新输入");
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
}
