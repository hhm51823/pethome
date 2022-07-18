package cn.raths.org.service.impl;

import cn.raths.basic.service.impl.BaseServiceImpl;
import cn.raths.basic.utils.MD5Utils;
import cn.raths.basic.utils.StrUtils;
import cn.raths.org.domain.Employee;
import cn.raths.org.mapper.EmployeeMapper;
import cn.raths.org.service.IEmployeeService;
import cn.raths.sys.domain.Role;
import cn.raths.user.domain.Logininfo;
import cn.raths.user.mapper.LogininfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceIMpl extends BaseServiceImpl<Employee> implements IEmployeeService {


    @Autowired
    private LogininfoMapper logininfoMapper;
    @Autowired
    private EmployeeMapper EmployeeMapper;


    public void removeEmployeeRole(Long id){
        EmployeeMapper.removeEmployeeRole(id);
    }
    public void saveEmployeeRole(Employee employee){
        if (employee.getRoles().size() > 0){
            EmployeeMapper.saveEmployeeRole(employee.getId(),employee.getRoles());
        }
    }
    @Override
    public void save(Employee employee) {
        // 初始化emp对象
        employeeInit(employee);
        EmployeeMapper.save(employee);
        // 初始化logininfo对象
        Logininfo logininfo = employee2Logininfo(employee);
        logininfoMapper.save(logininfo);
        // 设置logininfo id
        employee.setLogininfoId(logininfo.getId());
        EmployeeMapper.update(employee);

        saveEmployeeRole(employee);
    }

    @Override
    public void remove(Long id) {
        Employee employee = super.loadById(id);
        // 先删Logininfo
        if(employee != null) {
            logininfoMapper.remove(employee.getLogininfoId());
        }
        EmployeeMapper.remove(id);
        removeEmployeeRole(id);
    }

    @Override
    public void update(Employee employee) {
        // 初始化employee
        employeeInit(employee);
        // 修改数据
        EmployeeMapper.update(employee);
        // 赋值数据，这会覆盖掉id
        Logininfo logininfo = employee2Logininfo(employee);
        // 从新设置id
        logininfo.setId(employee.getLogininfoId());
        // 修改logininfo
        logininfoMapper.update(logininfo);

        removeEmployeeRole(employee.getId());
        saveEmployeeRole(employee);
    }

    private void employeeInit(Employee employee) {
        // 生成盐值
        String salt = StrUtils.getComplexRandomString(32);
        employee.setSalt(salt);
        // 根据MD5生成加密后的密码
        String saltPassword = MD5Utils.encrypByMd5(salt + employee.getPassword());

    }

    private Logininfo employee2Logininfo(Employee employee) {
        Logininfo logininfo = new Logininfo();
        BeanUtils.copyProperties(employee, logininfo);
        logininfo.setType(0);
        return logininfo;
    }

    @Override
    public Employee loadByLogininfoId(Long logininfoId) {
        return EmployeeMapper.loadByLogininfoId(logininfoId);
    }
}
