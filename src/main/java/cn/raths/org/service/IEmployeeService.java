package cn.raths.org.service;

import cn.raths.basic.service.IBaseService;
import cn.raths.org.domain.Employee;

public interface IEmployeeService extends IBaseService<Employee> {

    Employee loadByLogininfoId(Long id);
}
