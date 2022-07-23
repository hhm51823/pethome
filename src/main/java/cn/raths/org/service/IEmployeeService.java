package cn.raths.org.service;

import cn.raths.basic.service.IBaseService;
import cn.raths.org.domain.Employee;

import java.util.List;

public interface IEmployeeService extends IBaseService<Employee> {

    Employee loadByLogininfoId(Long id);

    List<Employee> loadByShopId(Long shopId);
}
