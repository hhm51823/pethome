package cn.raths.org.service.impl;

import cn.raths.basic.service.impl.BaseServiceImpl;
import cn.raths.org.domain.Employee;
import cn.raths.org.mapper.EmployeeMapper;
import cn.raths.org.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceIMpl extends BaseServiceImpl<Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;



    @Override
    public void save(Employee employee) {
        if(employee.getPassword() == null || "".equals(employee.getPassword().trim())){
            employee.setPassword("123456");
        }
        employeeMapper.save(employee);
    }
}
